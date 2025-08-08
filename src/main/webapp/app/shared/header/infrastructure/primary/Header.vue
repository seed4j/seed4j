<template>
  <header class="seed4j-header">
    <div class="seed4j-header--slot seed4j-with-theme-switch">
      <router-link class="seed4j-logo" to="/">
        <img class="seed4j-logo--icon" src="../../../../../content/JHipster-Lite-neon-blue.png" alt="JHipster bow tie" width="48" />
        <span class="seed4j-logo--text">Seed4J</span>
        <span class="seed4j-logo-version--text">{{ version }}</span>
      </router-link>
      <ThemeButtonVue />
    </div>
    <div class="seed4j-header--slot -expand"></div>
    <div class="seed4j-header--slot">
      <nav aria-label="Main navigation">
        <ul class="seed4j-nav">
          <li class="seed4j-nav--item">
            <router-link class="seed4j-nav-item" to="/landscape">
              <span class="seed4j-icon-text">
                <span class="seed4j-icon-text--icon">
                  <IconVue name="map" aria-label="Icon map" title="Module landscape" />
                </span>
                <span class="seed4j-icon-text--text">Landscape</span>
              </span>
            </router-link>
          </li>
          <li class="seed4j-nav--item">
            <router-link class="seed4j-nav-item" to="/patches">
              <span class="seed4j-icon-text">
                <span class="seed4j-icon-text--icon">
                  <IconVue name="puzzle" aria-label="Icon puzzle" title="Patch list" />
                </span>
                <span class="seed4j-icon-text--text">Patches</span>
              </span>
            </router-link>
          </li>
          <li class="seed4j-nav--item">
            <a class="seed4j-nav-item" href="https://seed4j.com" target="_blank" rel="noopener">
              <span class="seed4j-icon-text">
                <span class="seed4j-icon-text--icon">
                  <IconVue name="jhipster" aria-label="JHipster logo" title="Seed4J website" />
                </span>
                <span class="seed4j-icon-text--text -lg-only">JHipster</span>
              </span>
            </a>
          </li>
          <li class="seed4j-nav--item">
            <a class="seed4j-nav-item" href="https://github.com/seed4j/seed4j" target="_blank" rel="noopener">
              <span class="seed4j-icon-text">
                <span class="seed4j-icon-text--icon">
                  <IconVue name="github-circled" aria-label="GitHub logo" title="Seed4J GitHub page" />
                </span>
                <span class="seed4j-icon-text--text -lg-only">GitHub</span>
              </span>
            </a>
          </li>
          <li class="seed4j-nav--item">
            <a class="seed4j-nav-item" href="https://twitter.com/jhipster" target="_blank" rel="noopener">
              <span class="seed4j-icon-text">
                <span class="seed4j-icon-text--icon">
                  <IconVue name="twitter" aria-label="Twitter logo" title="JHipster Twitter" />
                </span>
                <span class="seed4j-icon-text--text -lg-only">Twitter</span>
              </span>
            </a>
          </li>
          <li class="seed4j-nav--item">
            <a class="seed4j-nav-item" href="https://opencollective.com/generator-jhipster" target="_blank" rel="noopener">
              <span class="seed4j-icon-text">
                <span class="seed4j-icon-text--icon">
                  <IconVue name="open-collective" aria-label="Open Collective logo" title="Open Collective JHipster page" />
                </span>
                <span class="seed4j-icon-text--text -lg-only">Open Collective</span>
              </span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </header>
</template>

<script lang="ts" src="./Header.component.ts"></script>
