<template>
  <HeaderVue />
  <main class="container-fluid py-4">
    <div class="row">
      <div class="col-12 col-md-4 p-4 shadow-primary bg-light">
        <h2>Project configuration</h2>
        <div class="row g-3">
          <div class="col-12">
            <label for="path" class="form-label">Path (required) : </label>
            <input id="path" v-model="project.folder" type="text" class="form-control" required autofocus />
          </div>
          <div class="col-12">
            <label for="basename" class="form-label">Basename : </label>
            <input id="basename" v-model="project.baseName" type="text" class="form-control" />
          </div>
          <div class="col-12">
            <label for="projectname" class="form-label">Project name : </label>
            <input id="projectname" v-model="project.projectName" type="text" class="form-control" />
          </div>
          <div class="col-12">
            <label for="packagename" class="form-label">Package name : </label>
            <input id="packagename" v-model="project.packageName" type="text" class="form-control" />
          </div>
          <div class="col-12">
            <label for="serverport" class="form-label">Server port : </label>
            <input id="serverport" v-model="project.serverPort" type="number" class="form-control" min="0" />
          </div>
        </div>
      </div>
      <div class="col">
        <h2>Options</h2>
        <div class="row row-cols-1 row-cols-md-2 g-4">
          <div class="col">
            <div class="card h-100 text-white bg-dark bg-gradient shadow">
              <h3 class="card-header h5 d-flex gap-3">
                <IconVue :name="'journal-code'" :aria-hidden="true" />
                Language
              </h3>
              <div class="card-body">
                <ul class="list-group--inline">
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input id="language" class="form-check-input flex-shrink-0" type="radio" name="language" value="java" checked />
                      <label for="language" role="button"> Java </label>
                    </div>
                    <img src="../../../content/java_rounded_40x40.png" alt="" width="32" height="32" class="rounded-circle flex-shrink-0" />
                  </li>
                </ul>
              </div>
            </div>
          </div>

          <div class="col">
            <div class="card h-100 text-white bg-primary bg-gradient shadow">
              <h3 class="card-header h5 d-flex gap-3">
                <IconVue :name="'tools'" :aria-hidden="true" />
                Build tool
              </h3>
              <div class="card-body">
                <ul class="list-group--inline">
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="build-tool-maven"
                        v-model="buildTool"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="buildTool"
                        value="maven"
                        checked
                      />
                      <label for="build-tool-maven" role="button"> Maven </label>
                    </div>
                    <img src="../../../content/MavenLogo.png" alt="" width="32" height="32" class="rounded-circle flex-shrink-0" />
                  </li>
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="build-tool-gradle"
                        v-model="buildTool"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="buildTool"
                        value="gradle"
                      />
                      <label for="build-tool-gradle" role="button"> Gradle </label>
                    </div>
                    <img src="../../../content/GradleLogo.png" alt="" width="32" height="32" class="rounded-circle flex-shrink-0" />
                  </li>
                </ul>
              </div>
            </div>
          </div>

          <div class="col">
            <div class="card h-100 text-white bg-secondary bg-gradient shadow">
              <h3 class="card-header h5 d-flex gap-3">
                <IconVue :name="'gear'" :aria-hidden="true" />
                Server
              </h3>
              <div class="card-body">
                <ul class="list-group--inline">
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="option-no-server"
                        v-model="server"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="server"
                        value="none"
                        checked
                      />
                      <label for="option-no-server" role="button"> None </label>
                    </div>
                    <img
                      src="../../../content/JHipster-Lite-neon-blue_40x.png"
                      alt=""
                      width="32"
                      height="32"
                      class="rounded-circle flex-shrink-0"
                    />
                  </li>
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="option-springboot"
                        v-model="server"
                        :data-selector="selectorPrefix + '.option-springboot'"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="server"
                        value="springboot"
                      />
                      <label for="option-springboot" role="button"> Spring Boot </label>
                    </div>
                    <img src="../../../content/SpringLogo.png" alt="" width="32" height="32" class="rounded-circle flex-shrink-0" />
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="card h-100 text-dark bg-light shadow">
              <h3 class="card-header h5 d-flex gap-3">
                <IconVue :name="'image-fill'" :aria-hidden="true" />
                Client
              </h3>
              <div class="card-body">
                <ul class="list-group--inline">
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="option-none-client"
                        v-model="client"
                        :data-selector="selectorPrefix + '.option-none-client'"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="client"
                        value="none"
                        checked
                      />
                      <label for="option-none-client" role="button"> None </label>
                    </div>
                    <img
                      src="../../../content/JHipster-Lite-neon-blue_40x.png"
                      alt=""
                      width="32"
                      height="32"
                      class="rounded-circle flex-shrink-0"
                    />
                  </li>
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="option-angular"
                        v-model="client"
                        :data-selector="selectorPrefix + '.option-angular'"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="client"
                        value="angular"
                      />
                      <label for="option-angular" role="button"> Angular </label>
                    </div>
                    <img src="../../../content/AngularLogo.png" alt="" width="32" height="32" class="rounded-circle flex-shrink-0" />
                  </li>
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="option-react"
                        v-model="client"
                        :data-selector="selectorPrefix + '.option-react'"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="client"
                        value="react"
                      />
                      <label for="option-react" role="button"> React </label>
                    </div>
                    <img src="../../../content/ReactLogo.png" alt="" width="32" height="32" class="rounded-circle flex-shrink-0" />
                  </li>
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="option-vue"
                        v-model="client"
                        :data-selector="selectorPrefix + '.option-vue'"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="client"
                        value="vue"
                      />
                      <label for="option-vue" role="button"> Vue.js </label>
                    </div>
                    <img src="../../../content/VueLogo.png" alt="" width="32" height="32" class="rounded-circle flex-shrink-0" />
                  </li>
                  <li class="list-group-item align-items-center gap-3 py-2" aria-current="true">
                    <div class="w-100 justify-content-between">
                      <input
                        id="option-svelte"
                        v-model="client"
                        :data-selector="selectorPrefix + '.option-svelte'"
                        class="form-check-input flex-shrink-0"
                        type="radio"
                        name="client"
                        value="svelte"
                      />
                      <label for="option-svelte" role="button"> Svelte </label>
                    </div>
                    <img src="../../../content/SvelteLogo.png" alt="" width="32" height="32" class="rounded-circle flex-shrink-0" />
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row mt-4 d-flex align-items-start shadow">
      <div class="col-12 col-md-4 col-lg-4 px-0">
        <ul id="v-pills-tab" class="nav nav-pills flex-column nav-fill" role="tablist" aria-orientation="vertical">
          <li class="nav-item">
            <div class="focus-helper" tabindex="-1"></div>
            <button
              id="section-init"
              class="nav-link text-primary bg-primary fw-bold active"
              data-bs-toggle="tab"
              data-bs-target="#v-pills-init"
              role="tab"
              aria-controls="v-pills-init"
              aria-selected="true"
            >
              <IconVue :name="'code-slash'" :aria-hidden="true" />
              INITIALIZATION
            </button>
          </li>

          <li v-if="server === 'springboot'" class="nav-item">
            <div class="q-focus-helper" tabindex="-1"></div>
            <button
              id="section-springboot"
              :data-selector="selectorPrefix + '.section-springboot'"
              class="nav-link bg-spring fw-bold text-spring"
              data-bs-toggle="tab"
              data-bs-target="#v-pills-springboot"
              role="tab"
              aria-controls="v-pills-springboot"
              aria-selected="false"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" x="0px" y="0px" viewBox="0 0 64 64" xml:space="preserve">
                <path
                  d="M58.2 3.365a29.503 29.503 0 0 1-3.419 6.064A32.094 32.094 0 1 0 9.965 55.372l1.186 1.047a32.08 32.08 0 0 0 52.67-22.253c.875-8.17-1.524-18.51-5.62-30.8zM14.53 55.558a2.744 2.744 0 1 1-.404-3.857 2.744 2.744 0 0 1 .404 3.857zm43.538-9.61c-7.92 10.55-24.83 6.99-35.672 7.502 0 0-1.922.113-3.857.43 0 0 .73-.31 1.663-.663 7.614-2.65 11.213-3.16 15.838-5.54 8.708-4.427 17.322-14.122 19.112-24.2-3.313 9.695-13.373 18.032-22.53 21.418-6.276 2.313-17.614 4.566-17.614 4.566l-.457-.245c-7.714-3.75-7.952-20.457 6.077-25.845 6.143-2.366 12.02-1.067 18.654-2.65 7.084-1.683 15.28-6.99 18.615-13.916 3.73 11.08 8.224 28.422.166 39.15z"
                  fill="#68bd45"
                />
              </svg>
              SPRING BOOT
            </button>
          </li>

          <li v-if="client === 'angular'" class="nav-item">
            <button
              id="section-angular"
              :data-selector="selectorPrefix + '.section-angular'"
              class="nav-link bg-angular fw-bold text-angular"
              data-bs-toggle="tab"
              data-bs-target="#v-pills-angular"
              type="button"
              role="tab"
              aria-controls="v-pills-angular"
              aria-selected="false"
            >
              <svg
                id="Layer_1"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                x="0px"
                y="0px"
                viewBox="0 0 240 240"
                width="24"
                height="24"
                xml:space="preserve"
              >
                <g>
                  <polygon
                    fill="#DD0031"
                    points="125,30 125,30 125,30 31.9,63.2 46.1,186.3 125,230 125,230 125,230 203.9,186.3 218.1,63.2 	"
                  />
                  <polygon
                    fill="#C3002F"
                    points="125,30 125,52.2 125,52.1 125,153.4 125,153.4 125,230 125,230 203.9,186.3 218.1,63.2 125,30 	"
                  />
                  <path
                    fill="#FFFFFF"
                    d="M125,52.1L66.8,182.6h0h21.7h0l11.7-29.2h49.4l11.7,29.2h0h21.7h0L125,52.1L125,52.1L125,52.1L125,52.1L125,52.1z M142,135.4H108l17-40.9L142,135.4z"
                  />
                </g>
              </svg>
              ANGULAR
            </button>
          </li>
          <li v-if="client === 'react'" class="nav-item">
            <button
              id="section-react"
              :data-selector="selectorPrefix + '.section-react'"
              class="nav-link text-primary bg-primary fw-bold text-react"
              data-bs-toggle="tab"
              data-bs-target="#v-pills-react"
              type="button"
              role="tab"
              aria-controls="v-pills-react"
              aria-selected="false"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="-11.5 -10.23174 23 20.46348">
                <title>React Logo</title>
                <circle cx="0" cy="0" r="2.05" fill="#61dafb" />
                <g stroke="#61dafb" stroke-width="1" fill="none">
                  <ellipse rx="11" ry="4.2" />
                  <ellipse rx="11" ry="4.2" transform="rotate(60)" />
                  <ellipse rx="11" ry="4.2" transform="rotate(120)" />
                </g>
              </svg>

              REACT
            </button>
          </li>
          <li v-if="client === 'vue'" class="nav-item">
            <button
              id="section-vue"
              :data-selector="selectorPrefix + '.section-vue'"
              class="nav-link fw-bold bg-vue bg-opacity-25 text-vue"
              data-bs-toggle="tab"
              data-bs-target="#v-pills-vue"
              type="button"
              role="tab"
              aria-controls="v-pills-vue"
              aria-selected="false"
            >
              <svg class="logo" viewBox="0 0 128 128" width="24" height="24" data-v-5f26462c="">
                <path fill="#42b883" d="M78.8,10L64,35.4L49.2,10H0l64,110l64-110C128,10,78.8,10,78.8,10z" data-v-5f26462c=""></path>
                <path fill="#35495e" d="M78.8,10L64,35.4L49.2,10H25.6L64,76l38.4-66H78.8z" data-v-5f26462c=""></path>
              </svg>
              VUE.JS
            </button>
          </li>
          <li v-if="client === 'svelte'" class="nav-item">
            <button
              id="section-svelte"
              :data-selector="selectorPrefix + '.section-vue'"
              class="nav-link bg-svelte fw-bold text-svelte"
              data-bs-toggle="tab"
              data-bs-target="#v-pills-svelte"
              type="button"
              role="tab"
              aria-controls="v-pills-svelte"
              aria-selected="false"
            >
              <img src="../../../content/SvelteLogo.png" alt="" width="24" height="24" class="rounded-circle flex-shrink-0" />
              SVELTE
            </button>
          </li>
        </ul>
      </div>

      <div class="col py-2">
        <div id="v-pills-tab" class="tab-content">
          <div id="v-pills-init" class="tab-pane fade show active" role="tabpanel" aria-labelledby="v-pills-home-tab">
            <div class="d-flex flex-column gap-3">
              <div>
                <DefaultButtonVue id="init" :label="'Init'" :data-selector="selectorPrefix + '.init-button'" @click.prevent="initProject" />
              </div>
              <div>
                <DefaultButtonVue
                  v-if="buildTool === 'maven'"
                  id="maven"
                  :label="'Maven'"
                  :data-selector="selectorPrefix + '.add-maven-button'"
                  @click.prevent="addMaven"
                />
                <DefaultButtonVue
                  id="jacoco"
                  :label="'JaCoCo'"
                  :data-selector="selectorPrefix + '.add-jacoco-button'"
                  @click.prevent="addJaCoCo"
                />
                <DefaultButtonVue
                  id="sonar-backend"
                  :label="'Sonar Backend'"
                  :data-selector="selectorPrefix + '.add-sonar-backend-button'"
                  @click.prevent="addSonarBackend"
                />
                <DefaultButtonVue
                  id="sonar-backend-frontend"
                  :label="'Sonar Backend+Frontend'"
                  :data-selector="selectorPrefix + '.add-sonar-backend-frontend-button'"
                  @click.prevent="addSonarBackendFrontend"
                />
              </div>
              <div>
                <DefaultButtonVue
                  id="javabase"
                  :label="'Java Base'"
                  :data-selector="selectorPrefix + '.add-java-base-button'"
                  @click.prevent="addJavaBase"
                />
              </div>
              <div>
                <DefaultButtonVue
                  id="frontend-maven-plugin"
                  :label="'Frontend Maven Plugin'"
                  :data-selector="selectorPrefix + '.add-frontend-maven-plugin-button'"
                  @click.prevent="addFrontendMavenPlugin"
                />
              </div>
              <div>
                <DefaultButtonVue
                  v-if="project.folder !== ''"
                  id="download"
                  :label="'Download'"
                  :data-selector="selectorPrefix + '.download-button'"
                  @click.prevent="download"
                />
              </div>
            </div>
          </div>
          <SpringBootGeneratorVue v-if="server === 'springboot'" :project="project" />
          <AngularGeneratorVue v-if="client === 'angular'" :project="project" />
          <ReactGeneratorVue v-if="client === 'react'" :project="project" />
          <VueGeneratorVue v-if="client === 'vue'" :project="project" />
          <SvelteGeneratorVue v-if="client === 'svelte'" :project="project" />
        </div>
      </div>
    </div>
  </main>
</template>

<script lang="ts" src="./Generator.component.ts"></script>
