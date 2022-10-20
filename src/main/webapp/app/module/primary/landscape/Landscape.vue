<template src="./Landscape.html"></template>

<script lang="ts" src="./Landscape.component.ts"></script>

<style lang="scss">
$jhipster-landscape-padding: 20px;

.jhipster-landscape {
  position: relative;
  font-family: $jhipster-lite-font-family;
  color: $jhipster-lite-primary-text-color;
  background-color: $jhipster-lite-secondary-color;
}

.jhipster-landscape-map {
  padding: $jhipster-landscape-padding;
  position: relative;
}

.jhipster-landscape-modes-selection {
  top: $jhipster-landscape-padding;
  left: $jhipster-landscape-padding;
  position: absolute;
  z-index: 3;
  background: rgba($color: $jhipster-lite-secondary-color, $alpha: 0.7);
  padding: 10px;
  border: 1px dotted $jhipster-lite-line-color;
  border-radius: $jhipster-lite-box-radius;

  &--modes {
    border: 1px solid $jhipster-lite-line-color;
    border-radius: 1.5em;
  }

  &--modes-switch {
    background-color: transparent;
    border: 0;
    border-radius: 1.5em;
    color: $jhipster-lite-primary-input-color;
    padding: 7px 15px;
    cursor: pointer;

    &.-selected {
      background-color: $jhipster-lite-primary-color;
    }
  }
}

.jhipster-landscape-connectors {
  position: absolute;
  top: $jhipster-landscape-padding;
  left: $jhipster-landscape-padding;
  z-index: 1;

  &--line {
    stroke: $jhipster-lite-line-color;
    stroke-opacity: 0.7;
    stroke-width: 1px;
    stroke-dasharray: 2 2;
    fill: none;

    &.-selectable-highlighted,
    &.-not-selectable-highlighted,
    &.-highlighted-unselection,
    &.-selected {
      stroke-dasharray: none;
      stroke-width: 3px;
      stroke-opacity: 1;
    }

    &.-selected {
      stroke: $jhipster-lite-primary-color;
    }

    &.-selectable-highlighted {
      animation: $jhipster-lite-selectable-highlight-animation;
    }

    &.-not-selectable-highlighted,
    &.-highlighted-unselection {
      animation: $jhipster-lite-not-selectable-highlight-animation;
    }
  }
}

.jhipster-landscape-levels {
  display: grid;
  z-index: 2;
  position: relative;
  place-items: center;

  &--level {
    grid-row: 1;
    padding: 0;

    &.-compacted {
      min-width: 150px;
      margin-right: 20px;
    }

    &.-extended {
      min-width: 250px;
      margin-right: 30px;
    }
  }
}

.jhipster-landscape-feature {
  border: 2px dotted $jhipster-lite-line-color;
  border-radius: $jhipster-lite-box-radius;
  background: rgba($color: $jhipster-lite-primary-alternative-color, $alpha: 0.3);

  &--title {
    text-align: center;
    font-size: 1em;
    font-weight: bold;

    &.-extended {
      margin: 5px;
    }

    &.-compacted {
      margin: 1px 3px;
    }
  }

  &--modules {
    padding: 0;
  }

  &--module {
    list-style-type: none;
  }

  &.-extended {
    padding: 10px 5px 0 5px;
  }

  &.-compacted {
    padding: 1px 3px 0 3px;
  }

  &.-selectable-highlighted {
    animation: $jhipster-lite-selectable-highlight-animation;
  }

  &.-not-selectable-highlighted,
  &.-highlighted-unselection {
    animation: $jhipster-lite-not-selectable-highlight-animation;
  }
}

.modules-application-button {
  margin-bottom: 15px;
  width: 100%;
  background-color: $jhipster-lite-primary-alternative-color;
  border: 1px solid $jhipster-lite-line-color;
  color: $jhipster-lite-primary-input-color;
  border-radius: 6px;
  padding: 9px 17px;

  &:disabled {
    background-color: $jhipster-lite-disabled-color;
  }
}
</style>
