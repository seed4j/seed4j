import { Service } from '@/common/domain/Service';

export type RestServiceId = string;

export const toService = (restServiceId: RestServiceId): Service => {
  switch (restServiceId) {
    case 'aop-logging':
      return Service.AOP_LOGGING;
    case 'angular':
      return Service.ANGULAR;
    case 'download':
      return Service.DOWNLOAD;
    case 'frontend-maven-plugin':
      return Service.FRONTEND_MAVEN_PLUGIN;
    case 'init':
      return Service.INITIALIZATION;
    case 'jacoco-check-min-coverage':
      return Service.JACOCO_CHECK_MINIMAL_COVERAGE;
    case 'java-base':
      return Service.JAVA_BASE;
    case 'logstash':
      return Service.LOGSTASH;
    case 'mariadb':
      return Service.MARIADB;
    case 'maven-java':
      return Service.MAVEN_JAVA;
    case 'mongodb':
      return Service.MONGODB;
    case 'mysql':
      return Service.MYSQL;
    case 'postgresql':
      return Service.POSTGRESQL;
    case 'sonar-java-backend':
      return Service.SONAR_JAVA_BACKEND;
    case 'sonar-java-backend-and-frontend':
      return Service.SONAR_JAVA_BACKEND_AND_FRONTEND;
    case 'springboot':
      return Service.SPRINGBOOT;
    case 'springboot-jwt':
      return Service.SPRINGBOOT_JWT;
    case 'springboot-jwt-basic-auth':
      return Service.SPRINGBOOT_JWT_WITH_BASIC_AUTHENTICATION;
    case 'springboot-actuator':
      return Service.SPRINGBOOT_ACTUATOR;
    case 'springboot-tomcat':
      return Service.SPRINGBOOT_MVC_WITH_TOMCAT;
    case 'springboot-webflux-netty':
      return Service.SPRINGBOOT_WEBFLUX_NETTY;
    case 'react':
      return Service.REACT;
    case 'react-styled':
      return Service.REACT_STYLED;
    case 'vue':
      return Service.VUE;
    case 'vue-styled':
      return Service.VUE_STYLED;
    default:
      return Service.UNKNOWN;
  }
};
