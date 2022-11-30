grammar Dsl;

file_
   : (config? context*) EOF
   | (context* config? ) EOF
   ;

config
   : 'config' configbody
   ;

context
   : 'context' IDENTIFIER contextBody
   ;

useFluentMethod
   : 'useFluentMethod' EGAL bool COMMA
   | 'useFluentMethod' EGAL bool
   ;
baseName
   : 'baseName' EGAL label
   | 'baseName' EGAL label COMMA
   ;

basePackageName
   : 'basePackageName' EGAL qualifiedName COMMA
   | 'basePackageName' EGAL qualifiedName
   ;

packageInfrastructureName
   : 'packageInfrastructureName' EGAL packageFormat COMMA
   | 'packageInfrastructureName' EGAL packageFormat
   ;

packageInfrastructurePrimaryName
   : 'packageInfrastructurePrimaryName' EGAL packageFormat COMMA
   | 'packageInfrastructurePrimaryName' EGAL packageFormat
   ;

packageInfrastructureSecondaryName
   : 'packageInfrastructureSecondaryName' EGAL packageFormat COMMA
   | 'packageInfrastructureSecondaryName' EGAL packageFormat
   ;

packageDomainName
   : 'packageDomainName' EGAL packageFormat COMMA
   | 'packageDomainName' EGAL packageFormat
   ;


projectFolder
   : 'projectFolder' EGAL directoryPath COMMA
   | 'projectFolder' EGAL directoryPath
   ;

label
   : STRING
   | IDENTIFIER
   ;

contextBody
   : LCURL (domain)* RCURL
   ;

domain
   : 'domain' domainBody
   ;

domainBody
    :LCURL (class)* RCURL
    ;

annotation
    : '@' label
    | '@' label LPAREN IDENTIFIER RPAREN
    | '@' label LPAREN packageFormat RPAREN
    ;

beforeClass
    : classType
    | (annotation)* classType
    | comment (annotation)* classType
    | (annotation)* comment classType
    ;

classType
    : ('class'
    | 'record')+
    ;

class
    : beforeClass IDENTIFIER classBody
    | beforeClass IDENTIFIER entityTableName classBody
    ;

classBody
    : LCURL (classField)*  RCURL
    ;

beforeClassField
    : comment?
    | comment? (annotation)*
    | comment? (modifiers)*
    | comment? (annotation)* (modifiers)*
    | (annotation)* comment?
    ;

classField
    : beforeClassField IDENTIFIER FIELD_TYPE_NUMBER comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_NUMBER (validation)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_NUMBER (validation)* (minMaxNumberValidator)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)* (validation)* comment? COMMA?
//    : comment? IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)*
//    | comment? IDENTIFIER FIELD_TYPE_NUMBER (validation)*
//    | comment? IDENTIFIER FIELD_TYPE_NUMBER (validation)* (minMaxNumberValidator)*
//    | comment? IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)* (validation)*
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB (minMaxByteValidator)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB (validation)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB (validation)* (minMaxByteValidator)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB (minMaxByteValidator)* (validation)* comment? COMMA?

    | beforeClassField IDENTIFIER FIELD_TYPE_STRING comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING (minMaxStringValidator)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING (validation | validatorPattern)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING (validation | validatorPattern)* (minMaxStringValidator)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING (minMaxStringValidator)* (validation | validatorPattern)* comment? COMMA?

    | beforeClassField IDENTIFIER FIELD_TYPE_TIME comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_TIME (validation)* comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_OTHER comment? COMMA?
    | beforeClassField IDENTIFIER FIELD_TYPE_OTHER (validation)* comment? COMMA?
    | beforeClassField IDENTIFIER IDENTIFIER comment? COMMA?
    | beforeClassField IDENTIFIER IDENTIFIER (validation)* comment? COMMA?
    ;

validatorPattern
    : 'pattern' LPAREN STRING_LITERAL* RPAREN
    ;

validation
    : REQUIRED
    | 'unique'
    ;
minMaxNumberValidator
    : 'min' LPAREN NATURAL_NUMBER RPAREN
    | 'max' LPAREN NATURAL_NUMBER RPAREN
    ;

minMaxStringValidator
    : 'minlength' LPAREN NATURAL_NUMBER RPAREN
    | 'maxlength' LPAREN NATURAL_NUMBER RPAREN
    ;

minMaxByteValidator
    : 'minbytes' LPAREN NATURAL_NUMBER RPAREN
    | 'maxbytes' LPAREN NATURAL_NUMBER RPAREN
    ;

primary
   : 'primary' IDENTIFIER primaryBody
   ;

secondary
   : 'secondary' IDENTIFIER secondaryBody
   ;

primaryBody
     :LCURL (STRING)* RCURL
     ;

secondaryBody
    :LCURL (STRING)* RCURL
    ;

configbody
   : LCURL (baseName
   | useFluentMethod
   | basePackageName
   | packageInfrastructureName
   | packageInfrastructurePrimaryName
   | packageInfrastructureSecondaryName
   | packageDomainName
   | projectFolder
   )* RCURL
   ;

entityTableName
    : LPAREN IDENTIFIER RPAREN
    ;

string
   : STRING
   | MULTILINESTRING
   ;

operator_
   : '/'
   | STAR
   | '%'
   | '+'
   | '-'
   | '>'
   | '>='
   | '<'
   | '<='
   | '=='
   | '!='
   | '&&'
   | '||'
   ;

bool
   : 'true'
   | 'false'
   | 'yes'
   | 'no'
   ;

qualifiedName
    : identifier ('.' identifier)*
;

directoryPath
    : '/' identifier ('/' identifier)*
;

packageFormat
    : qualifiedName
    ;

identifier
    : IDENTIFIER
    ;

number
   : NATURAL_NUMBER (DOT NATURAL_NUMBER)?
   ;

comment
    : BLOCKCOMMENT
    ;

modifiers
    : 'private'
    | 'public'
    | 'protected'
    | 'abstract'
    | 'static'
    | 'final'
    | 'transient'
    ;

EGAL
    : '='
    ;

REQUIRED
    : 'required'
    ;

FIELD_TYPE_OTHER
    : 'UUID'
    | 'Enum'
    ;

FIELD_TYPE_STRING
    : 'String'
    ;

FIELD_TYPE_TIME
    : 'Instant'
    | 'LocalDate'
    | 'ZonedDateTime'
    | 'Duration'
    | 'Period'
    ;

FIELD_TYPE_BLOB
    : 'Blob'
    | 'AnyBlob'
    | 'ImageBlob'
    | 'TextBlob'
    ;

FIELD_TYPE_NUMBER
    : 'Long'
    | 'Integer'
    | 'BigDecimal'
    | 'Float'
    | 'Double'
    ;

VARIABLE
   : 'variable'
   ;

PROVIDER
   : 'provider'
   ;

IN
   : 'in'
   ;

TO
   : 'to'
   ;

STAR
   : '*'
   ;

DOT
   : '.'
   ;

COMMA
    : ','
    ;

IDENTIFIER
    : Letter LetterOrDigit*
    ;

LCURL
   : '{'
   ;

RCURL
   : '}'
   ;
LSQUARE
    : '['
    ;
RSQUARE
    : ']'
    ;
LPAREN
   : '('
   ;

RPAREN
   : ')'
   ;

EOF_
   : '<<EOF' .*? 'EOF'
   ;

NULL_
   : 'null'
   ;

NATURAL_NUMBER
   : Digits+
   ;

BOOL
   : 'true'
   | 'false'
   | 'yes'
   | 'no'
   ;

DESCRIPTION
   : '<<DESCRIPTION' .*? 'DESCRIPTION'
   ;

MULTILINESTRING
   : '<<-EOF' .*? 'EOF'
   ;

STRING
   : '"' ( '\\"' | ~["\r\n] )* '"'
   | '\'' ( '\\\'' | ~["\r\n] )* '\''
   ;

STRING_LITERAL: 'a'..'z' | 'A'..'Z' | '0'..'9' | ':' | DOT | '&' | LPAREN | RPAREN | LSQUARE | RSQUARE| '$' | '@' | '/' | '\\' | ';' | '^';

COMMENT
  : ('#' | '//') ~ [\r\n]* -> channel(HIDDEN)
  ;

BLOCKCOMMENT
  : '/*' .*? '*/'
  ;

WS
   : [ \r\n\t]+ -> skip
   ;

NEWLINE :   '\r'? '\n' ;


fragment RESTOFLINE
        :   ~('\r'|'\n')*;

fragment HexDigits
    : HexDigit ((HexDigit | '_')* HexDigit)?
    ;

fragment HexDigit
    : [0-9a-fA-F]
    ;

fragment Digits
    : [0-9] ([0-9_]* [0-9])?
    ;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;