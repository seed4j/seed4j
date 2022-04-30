package tech.jhipster.lite.generator.client.angular.core.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.client.angular.core.domain.Angular.APP_COMPONENT;
import static tech.jhipster.lite.generator.client.angular.core.domain.Angular.APP_COMPONENT_HTML;
import static tech.jhipster.lite.generator.client.angular.core.domain.Angular.APP_COMPONENT_SPEC;
import static tech.jhipster.lite.generator.client.angular.core.domain.Angular.APP_MODULE;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class AngularDomainService implements AngularService {

  public static final String SOURCE = "client/angular";
  public static final String SOURCE_WEBAPP = "client/angular/src/main/webapp";
  private static final String APP = "src/main/webapp/app/common/primary/app";
  public static final String SOURCE_PRIMARY = getPath(SOURCE, APP);
  public static final String DESTINATION_PRIMARY = APP;

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public AngularDomainService(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Override
  public void addAngular(Project project) {
    addCommonAngular(project);
    addAppFiles(project);
  }

  @Override
  public void addJwtAngular(Project project) {
    addAppJwtFiles(project);
  }

  private void addCommonAngular(Project project) {
    addDependencies(project);
    addDevDependencies(project);
    addScripts(project);
    addJestSonar(project);
    addFiles(project);
    addAngularFiles(project);
  }

  public void addAppFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(project, SOURCE_PRIMARY, APP_COMPONENT_HTML, DESTINATION_PRIMARY);
    projectRepository.template(project, SOURCE_PRIMARY, APP_COMPONENT, DESTINATION_PRIMARY);

    addImages(project);
  }

  public void addAppJwtFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    addJwtDependencies(project);
    addAngularJwtFiles(project);
    addJwtFiles(project);
    updateAngularFilesForJwt(project);
  }

  public void addDependencies(Project project) {
    Angular.dependencies().forEach(dependency -> addDependency(project, dependency));
  }

  public void addDevDependencies(Project project) {
    Angular.devDependencies().forEach(devDependency -> addDevDependency(project, devDependency));
  }

  public void addJwtDependencies(Project project) {
    Angular.jwtDependencies().forEach(dependency -> addDependency(project, dependency));
  }

  public void updateAngularFilesForJwt(Project project) {
    String oldHtml = "<router-outlet></router-outlet>";
    String newHtml =
      """
        <div [ngSwitch]="account !== null">

          <form role="form" (ngSubmit)="login()" [formGroup]="loginForm" *ngSwitchCase="false">
            <div>
              <label class="username-label" for="username">Login</label>
              <input type="text" name="username" id="username" formControlName="username" #username />
            </div>
            <div>
              <label for="password">Password</label>
              <input type="password" name="password" id="password" formControlName="password" />
            </div>
            <button type="submit">Sign in</button>
          </form>

          <div *ngSwitchCase="true">
            <div>You are logged in as user "{{ account?.login }}".</div>
            <button (click)="logout()">Logout</button>
          </div>

        </div>

        <router-outlet></router-outlet>""";
    projectRepository.replaceText(project, APP, APP_COMPONENT_HTML, oldHtml, newHtml);

    oldHtml = "import \\{ Component, OnInit \\} from '@angular/core';";
    newHtml =
      """
        import { Component, OnDestroy, OnInit } from '@angular/core';
        import { FormBuilder, Validators } from '@angular/forms';
        import { Subject, takeUntil } from 'rxjs';
        import { AccountService } from '../../../auth/account.service';
        import { LoginService } from '../../../login/login.service';
        import { Account } from '../../../auth/account.model';""";
    projectRepository.replaceText(project, APP, APP_COMPONENT, oldHtml, newHtml);

    oldHtml = "export class AppComponent implements OnInit \\{";
    newHtml = "export class AppComponent implements OnInit, OnDestroy {";
    projectRepository.replaceText(project, APP, APP_COMPONENT, oldHtml, newHtml);

    oldHtml = "appName = '';";
    newHtml =
      """
      appName = '';

        private readonly destroy\\$ = new Subject<void>();

        account: Account | null = null;

        loginForm = this.fb.group({
          username: [null, [Validators.required]],
          password: [null, [Validators.required]]
        });

        constructor(
          private accountService: AccountService,
          private loginService: LoginService,
          private fb: FormBuilder
        ) {}
      """;
    projectRepository.replaceText(project, APP, APP_COMPONENT, oldHtml, newHtml);

    oldHtml = """
        }
      }
      """;
    newHtml =
      """
          this.accountService
            .getAuthenticationState()
            .pipe(takeUntil(this.destroy\\$))
            .subscribe(account => this.account = account);
        }

        ngOnDestroy(): void {
          this.destroy\\$.next();
          this.destroy\\$.complete();
        }

        login(): void {
          this.loginService
            .login({
              username: this.loginForm.get('username')!.value,
              password: this.loginForm.get('password')!.value,
            })
            .subscribe();
        }

        logout(): void {
          this.loginService.logout();
        }
      }""";
    projectRepository.replaceText(project, APP, APP_COMPONENT, oldHtml, newHtml);

    oldHtml = "import \\{ NgModule \\} from '@angular/core';";
    newHtml =
      """
        import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
        import { NgModule } from '@angular/core';
        import { ReactiveFormsModule } from '@angular/forms';
        import { NgxWebstorageModule } from 'ngx-webstorage';""";
    projectRepository.replaceText(project, APP, APP_MODULE, oldHtml, newHtml);

    oldHtml = "import \\{ AppComponent \\} from './app.component';";
    newHtml =
      """
        import { AppComponent } from './app.component';
        import { AuthInterceptor } from '../../../auth/auth.interceptor';
        """;
    projectRepository.replaceText(project, APP, APP_MODULE, oldHtml, newHtml);

    oldHtml = "imports: \\[BrowserModule, AppRoutingModule\\],";
    newHtml = "imports: [BrowserModule, AppRoutingModule, HttpClientModule, ReactiveFormsModule, NgxWebstorageModule.forRoot()],";
    projectRepository.replaceText(project, APP, APP_MODULE, oldHtml, newHtml);

    oldHtml = "bootstrap: \\[AppComponent\\],";
    newHtml =
      """
        bootstrap: [AppComponent],
          providers: [
            {
              provide: HTTP_INTERCEPTORS,
              useClass: AuthInterceptor,
              multi: true,
            },
          ],""";
    projectRepository.replaceText(project, APP, APP_MODULE, oldHtml, newHtml);

    oldHtml =
      """
      import \\{ ComponentFixture, TestBed, waitForAsync \\} from '@angular/core/testing';

      import \\{ AppComponent \\} from './app.component';

      describe\\('App Component', \\(\\) => \\{
        let comp: AppComponent;
        let fixture: ComponentFixture<AppComponent>;

        beforeEach\\(
          waitForAsync\\(\\(\\) => \\{
            TestBed.configureTestingModule\\(\\{
              declarations: \\[AppComponent\\],
            \\}\\)
              .overrideTemplate\\(AppComponent, ''\\)
              .compileComponents\\(\\);
          \\}\\)
        \\);

        beforeEach\\(\\(\\) => \\{
          fixture = TestBed.createComponent\\(AppComponent\\);
          comp = fixture.componentInstance;
        \\}\\);

        describe\\('ngOnInit', \\(\\) => \\{
          it\\('should have appName', \\(\\) => \\{
            // WHEN
            comp.ngOnInit\\(\\);

            // THEN
            expect\\(comp.appName\\).toEqual\\('""";
    newHtml =
      """
        import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
        import { HttpClientTestingModule } from '@angular/common/http/testing';
        import { NgxWebstorageModule } from 'ngx-webstorage';
        import { FormBuilder } from '@angular/forms';
        import { of, Subject } from 'rxjs';
        import { LoginService } from '../../../login/login.service';
        import { AccountService } from '../../../auth/account.service';
        import { Account } from '../../../auth/account.model';

        import { AppComponent } from './app.component';

        describe('App Component', () => {
          let comp: AppComponent;
          let fixture: ComponentFixture<AppComponent>;
          let mockAccountService: AccountService;
          let mockLoginService: LoginService;
          const account: Account = {
            activated: true,
            authorities: [],
            email: '',
            firstName: null,
            langKey: '',
            lastName: null,
            login: 'login',
          };

          beforeEach(
            waitForAsync(() => {
              TestBed.configureTestingModule({
                declarations: [AppComponent],
                imports: [HttpClientTestingModule, NgxWebstorageModule.forRoot()],
                providers: [
                  FormBuilder,
                  AccountService,
                  {
                    provide: LoginService,
                    useValue: {
                      login: jest.fn(() => of({})),
                      logout: jest.fn(() => of({})),
                    },
                  },
                ],
              })
                .overrideTemplate(AppComponent, '')
                .compileComponents();
            })
          );

          beforeEach(() => {
            fixture = TestBed.createComponent(AppComponent);
            comp = fixture.componentInstance;
            mockLoginService = TestBed.inject(LoginService);
            mockAccountService = TestBed.inject(AccountService);
          });

          describe('ngOnInit', () => {
            it('should have appName', () => {
              // GIVEN
              const authenticationState = new Subject<Account | null>();
              mockAccountService.getAuthenticationState = jest.fn(() => authenticationState.asObservable());

              // WHEN
              comp.ngOnInit();

              // THEN
              expect(comp.appName).toEqual('""";
    projectRepository.replaceText(project, APP, APP_COMPONENT_SPEC, oldHtml, newHtml);

    oldHtml = """
          \\}\\);
        \\}\\);

      \\}\\);""";
    newHtml =
      """
              expect(mockAccountService.getAuthenticationState).toHaveBeenCalled();

              // THEN
              expect(comp.account).toBeNull();

              // WHEN
              authenticationState.next(account);

              // THEN
              expect(comp.account).toEqual(account);

              // WHEN
              authenticationState.next(null);

              // THEN
              expect(comp.account).toBeNull();
            });
          });

          describe('login', () => {
            it('should authenticate the user', () => {
              // GIVEN
              const credentials = {
                username: 'admin',
                password: 'admin',
              };

              comp.loginForm.patchValue({
                username: 'admin',
                password: 'admin',
              });

              // WHEN
              comp.login();

              // THEN
              expect(mockLoginService.login).toHaveBeenCalledWith(credentials);
            });
          });

          describe('logout', () => {
            it('should logout the user', () => {
              // WHEN
              comp.logout();

              // THEN
              expect(mockLoginService.logout).toHaveBeenCalled();
            });
          });

        });
        """;
    projectRepository.replaceText(project, APP, APP_COMPONENT_SPEC, oldHtml, newHtml);

    oldHtml = "9000";
    newHtml = """
        9000,
                    "proxyConfig": "proxy.conf.json" """;
    projectRepository.replaceText(project, "", "angular.json", oldHtml, newHtml);
  }

  private void addDependency(Project project, String dependency) {
    npmService
      .getVersion("angular", dependency)
      .ifPresentOrElse(
        version -> npmService.addDependency(project, dependency, version),
        () -> {
          throw new GeneratorException("Dependency not found: " + dependency);
        }
      );
  }

  private void addDevDependency(Project project, String devDependency) {
    npmService
      .getVersion("angular", devDependency)
      .ifPresentOrElse(
        version -> npmService.addDevDependency(project, devDependency, version),
        () -> {
          throw new GeneratorException("DevDependency not found: " + devDependency);
        }
      );
  }

  public void addScripts(Project project) {
    Angular.scripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  public void addFiles(Project project) {
    Angular.files().forEach(file -> projectRepository.add(project, SOURCE, file));
  }

  public void addJwtFiles(Project project) {
    project.addConfig("serverPort", 8080);
    Angular.jwtFiles().forEach((file, path) -> projectRepository.template(project, getPath(SOURCE, path), file, getPath("", path)));
  }

  public void addAngularFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);
    Angular
      .angularFiles()
      .forEach((file, path) -> projectRepository.template(project, getPath(SOURCE_WEBAPP, path), file, getPath(MAIN_WEBAPP, path)));
  }

  public void addAngularJwtFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);
    Angular
      .angularJwtFiles()
      .forEach((file, path) -> projectRepository.template(project, getPath(SOURCE_WEBAPP, path), file, getPath(MAIN_WEBAPP, path)));
  }

  public void addImages(Project project) {
    projectRepository.add(
      project,
      getPath(SOURCE_WEBAPP, "content/images"),
      "JHipster-Lite-neon-red.png",
      "src/main/webapp/content/images"
    );
    projectRepository.add(project, getPath(SOURCE_WEBAPP, "content/images"), "AngularLogo.svg", "src/main/webapp/content/images");
  }

  public void addJestSonar(Project project) {
    String oldText = "\"cacheDirectories\": \\[";
    String newText =
      """
      "jestSonar": \\{
          "reportPath": "target/test-results/jest",
          "reportFile": "TESTS-results-sonar.xml"
        \\},
        "cacheDirectories": \\[""";
    projectRepository.replaceText(project, "", PACKAGE_JSON, oldText, newText);
  }
}
