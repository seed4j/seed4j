<template>
  <em
    class="jhlite-icon"
    :class="`jhlite-icon-${name}`"
    :aria-hidden="ariaHidden"
    :aria-label="!ariaHidden ? ariaLabel : undefined"
    :title="title"
  ></em>
</template>

<script lang="ts" src="./Icon.component.ts"></script>
