grammar Dsl;

file_
   : newLine* ( config? commentdsl* modules? commentdsl* context*)  EOF
   ;

newLine
    : NEWLINE
    ;

config
   : commentdsl? CONFIG configbody
   ;

modules
   : commentdsl? MODULES modulesbody
   ;

context
   : commentdsl? CONTEXT IDENTIFIER contextBody
   ;

useFluentMethod
   : CONFIG_USE_FLUENT_METHOD EGAL bool SEPARATOR_JHIPSTER*
   | CONFIG_USE_FLUENT_METHOD EGAL bool
   ;

baseName
   : CONFIG_BASENAME EGAL label SEPARATOR_JHIPSTER*
   | CONFIG_BASENAME EGAL label
   ;


basePackageName
   : CONFIG_BASE_PACKAGE_NAME EGAL qualifiedName SEPARATOR_JHIPSTER*
   | CONFIG_BASE_PACKAGE_NAME EGAL qualifiedName
   ;

packageInfrastructureName
   : CONFIG_PACKAGE_INFRASTRUCTURE_NAME EGAL packageFormat SEPARATOR_JHIPSTER*
   | CONFIG_PACKAGE_INFRASTRUCTURE_NAME EGAL packageFormat
   ;

packageInfrastructurePrimaryName
   : CONFIG_PACKAGE_INFRASTRUCTURE_PRIMARY_NAME EGAL packageFormat SEPARATOR_JHIPSTER*
   | CONFIG_PACKAGE_INFRASTRUCTURE_PRIMARY_NAME EGAL packageFormat
   ;

packageInfrastructureSecondaryName
   : CONFIG_PACKAGE_INFRASTRUCTURE_SECONDARY_NAME EGAL packageFormat SEPARATOR_JHIPSTER*
   | CONFIG_PACKAGE_INFRASTRUCTURE_SECONDARY_NAME EGAL packageFormat
   ;

packageDomainName
   : CONFIG_PACKAGE_DOMAIN EGAL packageFormat SEPARATOR_JHIPSTER*
   | CONFIG_PACKAGE_DOMAIN EGAL packageFormat
   ;

projectFolder
   : CONFIG_PROJECT_FOLDER EGAL directoryPath SEPARATOR_JHIPSTER*
   | CONFIG_PROJECT_FOLDER EGAL directoryPath
   ;


useAssertAsValidation
   : CONFIG_USE_ASSERT_VALIDATION EGAL bool SEPARATOR_JHIPSTER*
   | CONFIG_USE_ASSERT_VALIDATION EGAL bool
   ;

label
   : STRING
   | IDENTIFIER
   ;

contextBody
   : LCURL (domain | primary | secondary | commentdsl)* RCURL
   ;

domain
   : DOMAIN domainBody
   ;

primary
   : PRIMARY IDENTIFIER primaryBody
   ;

secondary
   : SECONDARY IDENTIFIER secondaryBody
   ;

domainBody
    :LCURL (class | enumType | commentdsl)* RCURL
    ;


beforeClass
    : classType
    | (annotationClass)* classType
    | comment (annotationClass)* classType
    | (annotationClass)* comment classType
    ;

classType
    : (CLASS_TYPE | RECORD_TYPE)+
    ;

class
    : beforeClass IDENTIFIER extend?  classBody //extends class?
    ;

extend
    : LPAREN IDENTIFIER RPAREN
    ;

classBody
    : LCURL (commentdsl | classField)*  RCURL
    ;

annotationClass
    : ('@package' | '@Package') LPAREN packageFormat RPAREN SEPARATOR_JHIPSTER*
    | ('@ignore' | '@Ignore') SEPARATOR_JHIPSTER*
    | ('@create' | '@Create') SEPARATOR_JHIPSTER*
    | ('@create' | '@Create') LPAREN IDENTIFIER RPAREN SEPARATOR_JHIPSTER*
    | ('@read' | '@Read') SEPARATOR_JHIPSTER*
    | ('@read' | '@Read') LPAREN IDENTIFIER RPAREN SEPARATOR_JHIPSTER*
    | ('@update' | '@Update') SEPARATOR_JHIPSTER*
    | ('@update' | '@Update') LPAREN IDENTIFIER RPAREN SEPARATOR_JHIPSTER*
    | ('@delete' | '@Delete') SEPARATOR_JHIPSTER*
    | ('@delete' | '@Delete') LPAREN IDENTIFIER RPAREN SEPARATOR_JHIPSTER*
    | ('@crud' | '@Crud') SEPARATOR_JHIPSTER*
    | ('@crud' | '@Crud') LPAREN IDENTIFIER RPAREN SEPARATOR_JHIPSTER*
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
    : beforeClassField IDENTIFIER list? VO? FIELD_TYPE_NUMBER comment? (constraintNumber)* SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_NUMBER (constraintNumber)* comment? SEPARATOR_JHIPSTER*


    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_BLOB comment? (constraintByte)* SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_BLOB (constraintByte)* comment? SEPARATOR_JHIPSTER*

    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_STRING comment? (constraintString)* SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_STRING (constraintString)* comment? SEPARATOR_JHIPSTER*

    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_TIME comment? (constraintCommon)* SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_TIME (constraintCommon)* comment? SEPARATOR_JHIPSTER*

    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_OTHER comment? (constraintCommon)* SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER list? VO? FIELD_TYPE_OTHER (constraintCommon)* comment? SEPARATOR_JHIPSTER*

    | beforeClassField IDENTIFIER list? VO? IDENTIFIER comment? (constraintCommon)* SEPARATOR_JHIPSTER*
    | beforeClassField IDENTIFIER list? VO? IDENTIFIER (constraintCommon)* comment? SEPARATOR_JHIPSTER*
    ;

list
    : '[]'
    ;

beforeClassField
    : (annotation)*
    | comment?
    | comment? (annotation)*
    | comment? (modifiers)*
    | comment? (annotation)* (modifiers)*
    | (annotation)* comment?
    ;

constraintCommon
  : REQUIRED
  | UNIQUE
  ;

constraintNumber
    : constraintCommon
    | minMaxNumberValidator
    ;

constraintString
    : constraintCommon
    | constraintPattern
    | minMaxStringValidator
    ;

constraintByte
    : constraintCommon
    | minMaxByteValidator
    ;

constraintPattern
    : 'pattern' LPAREN STRING RPAREN
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

primaryBody
    : LCURL (from | fromDomain | class | enumType | commentdsl)* RCURL
    ;

secondaryBody
    : LCURL (from | fromDomain | class | enumType | commentdsl)* RCURL
    ;

from
   : 'from' IDENTIFIER SEPARATOR_JHIPSTER*
   ;

fromDomain
   : 'fromDomain' SEPARATOR_JHIPSTER*
   ;

modulesbody
   : LCURL ( commentdsl )* RCURL
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
   | commentdsl
   )* RCURL SEPARATOR_JHIPSTER*
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

bool
    : BOOL_FALSE
    | BOOL_TRUE
    ;

number
   : NATURAL_NUMBER (DOT NATURAL_NUMBER)?
   ;

commentdsl
    : LINE_COMMENT SEPARATOR_JHIPSTER*
    ;

comment
    : BLOCKCOMMENT SEPARATOR_JHIPSTER*
    ;

modifiers
    : MODIFIERS
    ;

enumType
    : beforeEnum IDENTIFIER enumBody
    ;

beforeEnum
    : ENUM
    | (annotationClass)* ENUM
    | comment (annotationClass)* ENUM
    | (annotationClass)* comment ENUM
    ;
enumBody
    : LCURL (commentdsl? enumKeyValue SEPARATOR_JHIPSTER*)*  RCURL SEPARATOR_JHIPSTER*
    ;
//enumBody
//    : LCURL enumKeyValue (COMMA enumKeyValue)*  RCURL SEPARATOR_JHIPSTER*
//    | LCURL (enumKeyValue COMMA?)* RCURL SEPARATOR_JHIPSTER*
//    | LCURL enumKeyValue* SEPARATOR_JHIPSTER* RCURL SEPARATOR_JHIPSTER*
//    | LCURL (enumKeyValue SEPARATOR_JHIPSTER)*  RCURL SEPARATOR_JHIPSTER*
//    ;

enumKeyValue
    : beforeEnumKeyValue IDENTIFIER_UPPER
    | beforeEnumKeyValue IDENTIFIER_UPPER LPAREN STRING RPAREN
    ;

beforeEnumKeyValue
    : comment?
    ;


ENUM
    : 'enum'
    ;

VO
    : 'VO'
    | 'vo'
    | 'ValueObject'
    ;

CONFIG
    : 'config'
    ;

MODULES
    : 'modules'
    ;

CONTEXT
    : 'context'
    ;

DOMAIN
    : 'domain'
    ;

PRIMARY
    : 'primary'
    ;

SECONDARY
    : 'secondary'
    ;

CONFIG_USE_FLUENT_METHOD
    : 'useFluentMethod'
    ;

CONFIG_BASENAME
    : 'baseName'
    ;

CONFIG_BASE_PACKAGE_NAME
    : 'basePackageName'
    ;

CONFIG_PACKAGE_INFRASTRUCTURE_NAME
    : 'packageInfrastructureName'
    ;

CONFIG_PACKAGE_INFRASTRUCTURE_PRIMARY_NAME
    : 'packageInfrastructurePrimaryName'
    ;

CONFIG_PACKAGE_INFRASTRUCTURE_SECONDARY_NAME
    : 'packageInfrastructureSecondaryName'
    ;

CONFIG_PACKAGE_DOMAIN
    : 'packageDomainName'
    ;

CONFIG_PROJECT_FOLDER
    : 'projectFolder'
    ;

CONFIG_USE_ASSERT_VALIDATION
    : 'useAssertAsValidation'
    ;

CLASS_TYPE
    : 'class'
    ;

RECORD_TYPE
    : 'record'
    ;

MODIFIERS
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

UNIQUE
    : 'unique'
    ;

FIELD_TYPE_OTHER
    : 'UUID'
    | 'Enum'
    | 'Boolean'
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

BOOL_FALSE
    : ('no'
    | 'NO'
    | 'false'
    | 'FALSE')
    ;

BOOL_TRUE
    : ('yes'
    | 'YES'
    | 'true'
    | 'TRUE')
    ;

IDENTIFIER_UPPER
    : [A-Z]
    | [A-Z][A-Z_]*
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

LINE_COMMENT
    : '//' (WS | STRING_LITERAL)*
    ;

STRING_LITERAL: 'a'..'z' | 'A'..'Z' | '0'..'9' | ':' | DOT | '&' | LPAREN | RPAREN | LSQUARE | RSQUARE| '$' | '@' | '/' | '\\' | ';' | '^';

BLOCKCOMMENT
  : '/*' .*? '*/'
  ;

WS : (' ' | '\t' )+ -> skip;

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