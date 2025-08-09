# CORS configuration

You might want to configure Cross Origin Resources Sharing, here is a configuration example:

```properties
# CORS configuration
application.cors.allowed-origins=http://localhost:8100,http://localhost:9000
application.cors.allowed-methods=*
application.cors.allowed-headers=*
application.cors.exposed-headers=Authorization,Link,X-Total-Count,X-seed4j-alert,X-seed4j-error,X-seed4j-params
application.cors.allow-credentials=true
application.cors.max-age=1800
application.cors.allowed-origin-patterns=https://*.githubpreview.dev
```
