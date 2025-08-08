<template>
  <div class="seed4j-theme-button">
    <input
      id="switch"
      class="seed4j-theme-button-toggle"
      type="checkbox"
      name="mode"
      :checked="!isDarkTheme"
      data-testid="theme-switch-button"
      @change="toggleTheme"
    />
    <label for="switch" class="seed4j-theme-switch-label" :aria-label="theme">
      <span>🌙</span>
      <span>☀️</span>
      <div class="seed4j-theme-switch-toggle" :class="{ 'seed4j-theme-switch-toggle-checked': theme === 'dark-theme' }"></div>
    </label>
  </div>
</template>

<script lang="ts" src="./ThemeButton.component.ts"></script>
