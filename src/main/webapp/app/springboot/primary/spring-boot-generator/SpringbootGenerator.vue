<template>
  <div id="v-pills-springboot" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-profile-tab">
    <div class="d-flex flex-column gap-3">
      <div>
        <DefaultButtonVue
          id="springboot"
          :label="'Spring Boot'"
          :data-selector="selectorPrefix + '.add-spring-boot-button'"
          @click.prevent="addSpringBoot"
        />
        <DefaultButtonVue
          id="springbootmvctomcat"
          :label="'Spring MVC Tomcat'"
          :data-selector="selectorPrefix + '.add-spring-boot-mvc-tomcat-button'"
          @click.prevent="addSpringBootMvcTomcat"
        />
        <DefaultButtonVue
          id="springbootwebfluxnetty"
          :label="'Spring Webflux Netty'"
          :data-selector="selectorPrefix + '.add-spring-boot-webflux-netty-button'"
          @click.prevent="addSpringBootWebfluxNetty"
        />
        <DefaultButtonVue
          id="springboot-actuator"
          :label="'Spring Boot Actuator'"
          :data-selector="selectorPrefix + '.add-spring-boot-actuator-button'"
          @click.prevent="addSpringBootActuator"
        />
      </div>
      <div>
        <DefaultButtonVue
          id="springboot-aop"
          :label="'AOP Logging'"
          :data-selector="selectorPrefix + '.add-spring-boot-aop-button'"
          @click.prevent="addSpringBootAopLogging"
        />
        <DefaultButtonVue
          id="springboot-logstash"
          :label="'Logstash'"
          :data-selector="selectorPrefix + '.add-spring-boot-logstash-button'"
          @click.prevent="addSpringBootLogstash"
        />
      </div>
      <div>
        <DefaultButtonVue
          id="springboot-jwt"
          :label="'Security JWT'"
          :data-selector="selectorPrefix + '.add-spring-boot-jwt-button'"
          @click.prevent="addSpringBootSecurityJWT"
        />
        <DefaultButtonVue
          id="springboot-jwt-basic-auth"
          :label="'Security JWT Basic Auth'"
          :data-selector="selectorPrefix + '.add-spring-boot-jwt-basic-auth-button'"
          @click.prevent="addSpringBootSecurityJWTBasicAuth"
        />
      </div>
      <div>
        <DefaultButtonVue
          id="springboot-database-postgresql"
          :label="'PostgreSQL'"
          :data-selector="selectorPrefix + '.add-spring-boot-database-postgresql-button'"
          @click.prevent="addPostgreSQL"
        />
        <DefaultButtonVue
          id="springboot-database-mysql"
          :label="'MySQL'"
          :data-selector="selectorPrefix + '.add-spring-boot-database-mysql-button'"
          @click.prevent="addMySQL"
        />
        <DefaultButtonVue
          id="springboot-database-mariadb"
          :label="'MariaDB'"
          :data-selector="selectorPrefix + '.add-spring-boot-database-mariadb-button'"
          @click.prevent="addMariaDB"
        />
        <DefaultButtonVue
          id="springboot-database-mongodb"
          :label="'MongoDB'"
          :data-selector="selectorPrefix + '.add-spring-boot-database-mongodb-button'"
          @click.prevent="addMongoDB"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./SpringBootGenerator.component.ts"></script>
