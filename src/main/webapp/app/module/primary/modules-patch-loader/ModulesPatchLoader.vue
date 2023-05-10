<template src="./ModulesPatchLoader.html"></template>

<script lang="ts" src="./ModulesPatchLoader.component.ts"></script>

<style lang="scss">
.jhlite-modules-loader-content {
  background-color: $jhipster-lite-secondary-color;
  color: $jhipster-lite-primary-text-color;
  .ph-item {
    border: none;
    background-color: $jhipster-lite-secondary-color;
  }
}

.ph-jhlite-modules-container-content {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  flex-basis: 0;
  box-sizing: border-box;

  .modules-filter {
    height: 80px;
  }

  .modules-list {
    flex-grow: 1;
    overflow-y: auto;
  }
}

.ph-jhlite-modules-sidemenu {
  display: flex;
  flex-direction: column;
  min-height: 100%;
  box-sizing: border-box;

  &-top {
    flex-grow: 1;
    flex-basis: 0;
  }

  &-bottom {
    height: 110px;
    flex-grow: 0;
    flex-basis: auto;
  }
}
</style>
