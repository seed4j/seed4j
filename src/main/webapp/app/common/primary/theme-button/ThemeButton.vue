<template>
  <div class="jhlite-theme-button">
    <input
      id="switch"
      class="jhlite-theme-button-toggle"
      type="checkbox"
      name="mode"
      :checked="!isDarkTheme"
      data-selector="theme-switch-button"
      @change="toggleTheme"
    />
    <label for="switch" class="jhlite-theme-switch-label" :aria-label="theme">
      <span>🌙</span>
      <span>☀️</span>
      <div class="jhlite-theme-switch-toggle" :class="{ 'jhlite-theme-switch-toggle-checked': theme === 'dark-theme' }"></div>
    </label>
  </div>
</template>

<script lang="ts" src="./ThemeButton.component.ts"></script>
