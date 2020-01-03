package TuneOut;

import java_cup.runtime.*;

%%

%class DslLexer


%line
%column
%unicode

%cupsym DslSymbol
%cup

/* Declarations
 * The contents of this block is copied verbatim into the Lexer class, this
 * provides the ability to create member variable and methods to use in the
 * action blocks for rules.
*/
%{
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
        /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }

%}


/*
  Macro Declarations
*/

WhiteSpace = \p{Whitespace}
Number = -?\p{Digit}+
Note = ((([B-F]|[b-f]){1}(#|b|bb|x)?(-1|[0-9])?) | (([A|G]|[a|g]){1}(-1|[0-9]?)) | (([A|G]|[a|g]){1}((#|b|bb|x)?)(-1|[0-8])?))
Overrides = (-([A-Z] | [a-z])){1}
String = (([a-z] | [A-Z])+)


%%

/* Rules */

<YYINITIAL> {
    "showVersion"             { return symbol(DslSymbol.COMMAND_SHOWVERSION); }
    "midi"                    { return symbol(DslSymbol.COMMAND_MIDI); }
    "note"                    { return symbol(DslSymbol.COMMAND_NOTE); }
    "semitone"                { return symbol(DslSymbol.COMMAND_SEMITONE); }
    "enharmonic"              { return symbol(DslSymbol.COMMAND_ENHARMONIC); }
    "enharmonicHigh"          { return symbol(DslSymbol.COMMAND_ENHARMONICHIGH); }
    "enharmonicLow"           { return symbol(DslSymbol.COMMAND_ENHARMONICLOW); }
    "help"                    { return symbol(DslSymbol.COMMAND_HELP); }
    "play"                    { return symbol(DslSymbol.COMMAND_PLAY); }
    "("                       { return symbol(DslSymbol.OPEN_PARENTHESIS); }
    ")"                       { return symbol(DslSymbol.CLOSE_PARENTHESIS); }
    "\""                       { return symbol(DslSymbol.QUOTATION_MARK); }
    ","                       { return symbol(DslSymbol.COMMA); }
    "tempo"                   { return symbol(DslSymbol.COMMAND_TEMPO); }
    "setTempo"                { return symbol(DslSymbol.COMMAND_SET_TEMPO); }
    "categoryTerm"            { return symbol(DslSymbol.COMMAND_CATEGORY_TERM); }
    {Number}                  { return symbol(DslSymbol.NUMBER, new Integer(yytext())); }
    {WhiteSpace}              { /* Ignore whitespace */ }
    {Note}                    { return symbol(DslSymbol.NOTE, yytext());}
    {String}                  { return symbol(DslSymbol.STRING, yytext());}
    {Overrides}               { return symbol(DslSymbol.OVERRIDES, yytext());}


}

/* Throw an exception if we have no matches */
[^]                    { throw new RuntimeException("Illegal character <"+yytext()+">"); }