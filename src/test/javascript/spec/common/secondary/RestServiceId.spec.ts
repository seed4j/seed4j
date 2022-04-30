import { Service } from '@/common/domain/Service';
import { toService } from '@/common/secondary/RestServiceId';

describe('RestServiceId', () => {
  it('should convert to Service', () => {
    expect(toService('aop-logging')).toEqual<Service>(Service.AOP_LOGGING);
    expect(toService('angular')).toEqual<Service>(Service.ANGULAR);
    expect(toService('download')).toEqual<Service>(Service.DOWNLOAD);
    expect(toService('frontend-maven-plugin')).toEqual<Service>(Service.FRONTEND_MAVEN_PLUGIN);
    expect(toService('init')).toEqual<Service>(Service.INITIALIZATION);
    expect(toService('jacoco-check-min-coverage')).toEqual<Service>(Service.JACOCO_CHECK_MINIMAL_COVERAGE);
    expect(toService('java-base')).toEqual<Service>(Service.JAVA_BASE);
    expect(toService('logstash')).toEqual<Service>(Service.LOGSTASH);
    expect(toService('mariadb')).toEqual<Service>(Service.MARIADB);
    expect(toService('maven-java')).toEqual<Service>(Service.MAVEN_JAVA);
    expect(toService('mongodb')).toEqual<Service>(Service.MONGODB);
    expect(toService('mysql')).toEqual<Service>(Service.MYSQL);
    expect(toService('postgresql')).toEqual<Service>(Service.POSTGRESQL);
    expect(toService('sonar-java-backend')).toEqual<Service>(Service.SONAR_JAVA_BACKEND);
    expect(toService('sonar-java-backend-and-frontend')).toEqual<Service>(Service.SONAR_JAVA_BACKEND_AND_FRONTEND);
    expect(toService('springboot')).toEqual<Service>(Service.SPRINGBOOT);
    expect(toService('springboot-jwt')).toEqual<Service>(Service.SPRINGBOOT_JWT);
    expect(toService('springboot-jwt-basic-auth')).toEqual<Service>(Service.SPRINGBOOT_JWT_WITH_BASIC_AUTHENTICATION);
    expect(toService('springboot-actuator')).toEqual<Service>(Service.SPRINGBOOT_ACTUATOR);
    expect(toService('springboot-tomcat')).toEqual<Service>(Service.SPRINGBOOT_MVC_WITH_TOMCAT);
    expect(toService('springboot-webflux-netty')).toEqual<Service>(Service.SPRINGBOOT_WEBFLUX_NETTY);
    expect(toService('react')).toEqual<Service>(Service.REACT);
    expect(toService('react-styled')).toEqual<Service>(Service.REACT_STYLED);
    expect(toService('vue')).toEqual<Service>(Service.VUE);
    expect(toService('vue-styled')).toEqual<Service>(Service.VUE_STYLED);
    expect(toService('beer')).toEqual<Service>(Service.UNKNOWN);
  });
});
