<template src="./LandscapeLoader.html"></template>

<script lang="ts" src="./LandscapeLoader.component.ts"></script>

<style lang="scss">
$jhipster-landscape-padding: 20px;

.jhlite-landscape-loader-content {
  position: relative;
  .ph-item {
    border: none;
    padding: 0px;
  }
}

.ph-jhlite-landscape-modes-selection {
  position: absolute;
  z-index: 3;
  top: $jhipster-landscape-padding;
  left: $jhipster-landscape-padding;
  width: 220px;

  .ph-item {
    padding: 0px;
  }

  .ph-row {
    height: 30px;
  }
}

.ph-jhlite-landscape-map {
  padding: $jhipster-landscape-padding;
}

.ph-jhlite-landscape-levels {
  position: relative;
  display: grid;
  z-index: 2;
  place-items: center;

  &--level {
    grid-row: 1;
    padding: 0;
    min-width: 180px;
  }
}

.ph-jhlite-landscape-element {
  list-style-type: none;
  margin-bottom: 5px;
}

.ph-jhlite-landscape-sidemenu {
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  min-height: 100%;

  &-top {
    flex-basis: 0;
    flex-grow: 1;
  }

  &-bottom {
    flex-basis: auto;
    flex-grow: 0;
    height: 118px;
  }
}
</style>
