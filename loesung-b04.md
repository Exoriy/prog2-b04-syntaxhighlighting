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



