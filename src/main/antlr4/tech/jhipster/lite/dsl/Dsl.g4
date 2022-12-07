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
   : 'useFluentMethod' EGAL bool SEPARATOR_JHIPSTER*
   | 'useFluentMethod' EGAL bool
   ;
baseName
   : 'baseName' EGAL label SEPARATOR_JHIPSTER*
   | 'baseName' EGAL label
   ;

basePackageName
   : 'basePackageName' EGAL qualifiedName SEPARATOR_JHIPSTER*
   | 'basePackageName' EGAL qualifiedName
   ;

packageInfrastructureName
   : 'packageInfrastructureName' EGAL packageFormat SEPARATOR_JHIPSTER*
   | 'packageInfrastructureName' EGAL packageFormat
   ;

packageInfrastructurePrimaryName
   : 'packageInfrastructurePrimaryName' EGAL packageFormat SEPARATOR_JHIPSTER*
   | 'packageInfrastructurePrimaryName' EGAL packageFormat
   ;

packageInfrastructureSecondaryName
   : 'packageInfrastructureSecondaryName' EGAL packageFormat SEPARATOR_JHIPSTER*
   | 'packageInfrastructureSecondaryName' EGAL packageFormat
   ;

packageDomainName
   : 'packageDomainName' EGAL packageFormat SEPARATOR_JHIPSTER*
   | 'packageDomainName' EGAL packageFormat
   ;


projectFolder
   : 'projectFolder' EGAL directoryPath SEPARATOR_JHIPSTER*
   | 'projectFolder' EGAL directoryPath
   ;

useAssertAsValidation
   : 'useAssertAsValidation' EGAL bool SEPARATOR_JHIPSTER*
   | 'useAssertAsValidation' EGAL bool
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


beforeClass
    : classType
    | (annotationClass)* classType
    | comment (annotationClass)* classType
    | (annotationClass)* comment classType
    ;

classType
    : ('class'
    | 'record')+
    ;

class
    : beforeClass IDENTIFIER classBody
    ;

classBody
    : LCURL (classField)*  RCURL
    ;

annotationClass
    : ('@package' | '@Package') LPAREN packageFormat RPAREN SEPARATOR_JHIPSTER*
    ;

annotation
    : ('@min' | '@Min') LPAREN NATURAL_NUMBER RPAREN SEPARATOR_JHIPSTER*
    | ('@max' | '@Max') LPAREN NATURAL_NUMBER RPAREN SEPARATOR_JHIPSTER*
    | ('@decimalmin' | '@Decimalmin' | '@decimalMin' | '@DecimalMin') LPAREN FLOAT RPAREN SEPARATOR_JHIPSTER*
    | ('@decimalmax' | '@Decimalmax'| '@decimalMax' | '@DecimalMax') LPAREN FLOAT RPAREN SEPARATOR_JHIPSTER*
    | ('@before' | '@Before') SEPARATOR_JHIPSTER*
    | ('@past' | '@Past')SEPARATOR_JHIPSTER*
    | ('@future' | '@Future')SEPARATOR_JHIPSTER*
    | ('@futureOrPresent' | '@FutureOrPresent')SEPARATOR_JHIPSTER*
    | ('@PastOrPresent' | '@PastOrPresent')SEPARATOR_JHIPSTER*
    | ('@notEmpty' | '@NotEmpty')SEPARATOR_JHIPSTER*
    | ('@negative' | '@Negative')SEPARATOR_JHIPSTER*
    | ('@positive' | '@Positive')SEPARATOR_JHIPSTER*
    | ('@assertFalse' | '@AssertFalse')SEPARATOR_JHIPSTER*
    | ('@assertTrue' | '@AssertTrue')SEPARATOR_JHIPSTER*
    | ('@notBlank' | '@NotBlank' | '@notblank')SEPARATOR_JHIPSTER*
    | ('@nullable' | '@Nullable' | '@Null' | '@null')SEPARATOR_JHIPSTER*
    | ('@notNull' | '@NotNull' | '@notnull' | '@Notnull')SEPARATOR_JHIPSTER*
    ;

classField
    : beforeClassField IDENTIFIER FIELD_TYPE_NUMBER comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_NUMBER (validation)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_NUMBER (validation)* (minMaxNumberValidator)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)* (validation)* comment? SEPARATOR_JHIPSTER*

    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB (minMaxByteValidator)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB (validation)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB (validation)* (minMaxByteValidator)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_BLOB (minMaxByteValidator)* (validation)* comment? SEPARATOR_JHIPSTER*
//
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING (minMaxStringValidator)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING (validation | validatorPattern)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING (validation | validatorPattern)* (minMaxStringValidator)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_STRING (minMaxStringValidator)* (validation | validatorPattern)* comment? SEPARATOR_JHIPSTER*

    | beforeClassField IDENTIFIER FIELD_TYPE_TIME comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_TIME (validation)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_OTHER comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER FIELD_TYPE_OTHER (validation)* comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER IDENTIFIER comment? SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER IDENTIFIER (validation)* comment? SEPARATOR_JHIPSTER*
    ;
beforeClassField
    : (annotation)*
    | comment?
    | comment? (annotation)*
    | comment? (modifiers)*
    | comment? (annotation)* (modifiers)*
    | (annotation)* comment?

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
   | useAssertAsValidation
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
    : BLOCKCOMMENT SEPARATOR_JHIPSTER*
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

SEPARATOR_JHIPSTER
 : (COMMA| NEWLINE)
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
   | '{'NEWLINE*
   ;

RCURL
   : '}'
   | '}'NEWLINE*
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

FLOAT   : Digits+ '.' Digits*
        | '.' Digits+
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

//WS
//   : [ \r\n\t]+ -> skip
//   ;
//WS
//  : (' ' | '\t')+-> skip
//  ;
WS : (' ' | '\t')+ -> channel(HIDDEN);
NEWLINE :  WS* '\r'? '\n' ;


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
//    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
//    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;