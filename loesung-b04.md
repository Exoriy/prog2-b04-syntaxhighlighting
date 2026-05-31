# Lösung zu Blatt 04: RegEx, Template-Method; JUnit; PR

## Überblick

Dieses Repository enthält meine Bearbeitung zu Blatt 04.

Bearbeitet werden folgende Aufgaben:

1. Reguläre Ausdrücke für das Syntaxhighlighting in (`MiniJavaTokens`)
2. Syntaxhighlighting mit dem `RegexHighlighter`
4. Git: Pull‑Requests und CI

## Repository

`https://github.com/Exoriy/prog2-b04-syntaxhighlighting`



---

## Aufgabe 1: MiniJavaTokens

In dieser Aufgabe habe ich die Klasse `MiniJavaTokens` ergänzt. In dieser Klasse stehen die regulären Ausdrücke für das Syntaxhighlighting.

Ich habe Tokens für folgende Sachen hinzugefügt:

- Javadoc-Kommentare
- Block-Kommentare
- Einzeilige Kommentare
- String-Literale
- Character-Literale
- Keywords
- Annotationen

Wichtig war die Reihenfolge der Tokens. Der Javadoc-Kommentar steht vor dem normalen Block-Kommentar, weil beide ähnlich anfangen. Wenn der normale Block-Kommentar zuerst kommen würde, könnte ein Javadoc-Kommentar falsch erkannt werden.

Bei den Keywords habe ich Wortgrenzen benutzt. Dadurch wird zum Beispiel `class` als Keyword erkannt, aber `class` in `classroom` nicht.

Für Annotationen habe ich einen einfachen regulären Ausdruck benutzt. Er beginnt mit `@` und erlaubt danach Buchstaben und Minuszeichen.

Für diese Tokens habe ich JUnit-Tests geschrieben.

In den Tests prüfe ich unter anderem:

- Javadoc-Kommentare
- Block-Kommentare
- Einzeilige Kommentare
- Strings
- Characters
- Keywords
- Annotationen
- Keywords als ganze Wörter
- Strings mit `//` im Inhalt
- Den Fall, dass kein Token gefunden wird

Ich habe das Projekt auch lokal geprüft mit:

```bash
.\gradlew spotlessApply
.\gradlew spotlessCheck
.\gradlew test
.\gradlew build
```

Und alle Tests waren erfolgreich.



---

## Aufgabe 2: RegexHighlighter

In dieser Aufgabe habe ich den `RegexHighlighter` implementiert.

Der `RegexHighlighter` benutzt die Tokens aus `MiniJavaTokens`. In der Methode `collectMatches` werden alle Tokens nacheinander auf den ganzen Text angewendet. Alle gefundenen Treffer werden in einer Liste gesammelt.

Dafür habe ich eine Schleife benutzt:

```java
for (Token token : MiniJavaTokens.defaultTokens()) {
  regions.addAll(token.test(text));
}
```

Danach müssen Konflikte gelöst werden. Ein Konflikt entsteht, wenn sich zwei `HighlightRegion`-Objekte überschneiden. Das kann zum Beispiel passieren, wenn ein Keyword in einem Kommentar steht.

In `resolveConflicts` gehe ich durch die Liste der Regionen. Wenn eine Region sich mit einer schon übernommenen Region überschneidet, wird sie nicht mehr hinzugefügt. Dadurch bleibt die Region erhalten, die zuerst in der sortierten Liste steht.

Für die Prüfung der Überschneidung habe ich diese Bedingung benutzt:

```java
return first.start() < second.end() && second.start() < first.end();
```

Wichtig ist dabei, dass direkt benachbarte Regionen nicht als Konflikt zählen. Zum Beispiel überschneiden sich `[0,5)` und `[5,10)` nicht.

Für den `RegexHighlighter` habe ich JUnit-Tests geschrieben. Die Tests prüfen unter anderem:

- dass Keywords gefunden werden
- dass `collectMatches` auch überlappende Treffer sammelt
- dass `resolveConflicts` überlappende Regionen entfernt
- dass benachbarte Regionen erhalten bleiben
- dass ein Keyword innerhalb eines Kommentars nicht extra markiert wird
- dass bei leerem Text keine Treffer entstehen
- dass bei normalem Text ohne Java-Tokens keine Treffer entstehen

Ich habe das Projekt auch lokal geprüft mit:

```bash
.\gradlew spotlessApply
.\gradlew spotlessCheck
.\gradlew test
.\gradlew build
```

Und alle Tests waren erfolgreich.



