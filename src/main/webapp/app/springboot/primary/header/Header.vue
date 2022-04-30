<template>
  <header class="navbar navbar-expand-md bg-primary-dark bd-navbar shadow mb-3">
    <nav class="container-fluid flex-wrap flex-md-nowrap align-text-center" aria-label="Main navigation">
      <div class="navbar-brand text-decoration-none d-flex align-items-center gap-4">
        <img src="../../../../content/JHipster-Lite-neon-blue.png" alt="" width="48" />
        <span class="fs-3 text-decoration-none text-white title" :data-selector="selectorPrefix + '.title'">JHipster lite</span>
      </div>
      <button
        class="navbar-toggler text-primary"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#bdNavbar"
        aria-controls="bdNavbar"
        aria-expanded="false"
        aria-label="Toggle navigation"
        width="36"
        height="36"
        fill-rule="evenodd"
      >
        <svg class="bi" viewBox="0 0 16 16" width="24" height="24" fill="#4ba8f8" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M2.5 11.5A.5.5 0 0 1 3 11h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4A.5.5 0 0 1 3 7h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4A.5.5 0 0 1 3 3h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"
          ></path>
        </svg>
      </button>

      <div id="bdNavbar" class="collapse navbar-collapse fs-4">
        <hr class="d-md-none text-white-50" />

        <ul class="navbar-nav flex-row flex-wrap ms-md-auto">
          <li class="nav-item col-6 col-md-auto">
            <a class="nav-link p-2" href="./swagger-ui.html" target="_blank" rel="noopener">
              <IconVue :name="'book-half'" :aria-label="'API'" />
              <small class="d-md-none ms-2">API</small>
            </a>
          </li>
          <hr class="separator--vertical mx-2" />
          <li class="nav-item col-6 col-md-auto">
            <a class="nav-link p-2" href="https://www.jhipster.tech/" target="_blank" rel="noopener">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="48"
                height="36"
                fill="currentColor"
                fill-rule="evenodd"
                class="navbar-nav-svg d-inline-block"
                viewBox="0 0 120 60"
                role="img"
                aria-label="JHipster"
              >
                <g id="a" />
                <g id="b">
                  <g id="c">
                    <g id="d">
                      <path
                        fill-opacity=".4"
                        id="e"
                        class="g"
                        d="M69.88,20.11S10.53-14.33,5.4,7.08C.27,28.5-5.5,44.79,10.31,47.8c15.81,3.01,59.57-27.69,59.57-27.69Z"
                      />
                      <path
                        id="f"
                        class="h"
                        d="M59.62,14.93c17.04-8.81,50.67-23.88,54.52-7.92,5.18,21.49,10.98,37.84-4.78,40.91-11.08,2.16-35.99-12.37-49.82-21.2-.01-10.44,.1-1.51,.09-11.79Z"
                      />
                    </g>
                  </g>
                </g>
              </svg>
              <small class="d-md-none ms-2">JHipster</small>
            </a>
          </li>
          <li class="nav-item col-6 col-md-auto">
            <a class="nav-link p-2" href="https://github.com/jhipster/jhipster-lite" target="_blank" rel="noopener">
              <IconVue :name="'github'" :aria-label="'GitHub'" />
              <small class="d-md-none ms-2">GitHub</small>
            </a>
          </li>
          <li class="nav-item col-6 col-md-auto">
            <a class="nav-link p-2" href="https://twitter.com/jhipster" target="_blank" rel="noopener">
              <IconVue :name="'twitter'" :aria-label="'Twitter'" />
              <small class="d-md-none ms-2">Twitter</small>
            </a>
          </li>

          <li class="nav-item col-6 col-md-auto">
            <a class="nav-link p-2" href="https://opencollective.com/generator-jhipster" target="_blank" rel="noopener">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="36"
                height="36"
                fill="currentColor"
                fill-rule="evenodd"
                class="navbar-nav-svg d-inline-block"
                viewBox="0 0 52 52"
                role="img"
                aria-label="Open collective"
              >
                <title>Open Collective</title>
                <path
                  fill-opacity=".4"
                  d="M32.8 21c0 2.4-.8 4.9-2 6.9l5.1 5.1c2.5-3.4 4.1-7.6 4.1-12 0-4.6-1.6-8.8-4-12.2L30.7 14c1.2 2 2 4.3 2 7z"
                ></path>
                <path d="M20 33.7a12.8 12.8 0 0 1 0-25.6c2.6 0 5 .7 7 2.1L32 5a20 20 0 1 0 .1 31.9l-5-5.2a13 13 0 0 1-7 2z"></path>
              </svg>
              <small class="d-md-none">Open Collective</small>
            </a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script lang="ts" src="./Header.component.ts"></script>
