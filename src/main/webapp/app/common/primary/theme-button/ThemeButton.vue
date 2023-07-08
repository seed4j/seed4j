<template>
  <div class="jhlite-container">
    <input
      id="switch"
      class="jhlite-container_toggle"
      type="checkbox"
      name="mode"
      :checked="!isDarkTheme"
      data-selector="theme-switch-button"
      @change="toggleTheme"
    />
    <label for="switch" class="jhlite-theme-switch_label" :aria-label="theme">
      <span>🌙</span>
      <span>☀️</span>
      <div class="jhlite-theme-switch_toggle" :class="{ 'jhlite-theme-switch_toggle-checked': theme === 'dark-theme' }"></div>
    </label>
  </div>
</template>

<script lang="ts" src="./ThemeButton.component.ts"></script>

<style lang="scss">
.jhlite-container {
  display: flex;
}

.jhlite-container_toggle {
  height: 0;
  width: 0;
  visibility: hidden;
}

.jhlite-theme-switch_label {
  align-items: center;
  background: var(--jhlite-text-color-primary);
  border: calc(var(--jhlite-element-size) * 0.025) solid var(--jhlite-accent-color);
  border-radius: var(--jhlite-element-size);
  cursor: pointer;
  display: flex;
  font-size: calc(var(--jhlite-element-size) * 0.3);
  height: calc(var(--jhlite-element-size) * 0.35);
  position: relative;
  padding: calc(var(--jhlite-element-size) * 0.1);
  transition: background 0.5s ease;
  justify-content: space-between;
  width: var(--jhlite-element-size);
  z-index: 1;
}

.jhlite-theme-switch_toggle {
  position: absolute;
  background-color: var(--jhlite-bg-color-primary);
  border-radius: 50%;
  top: calc(var(--jhlite-element-size) * 0.07);
  left: calc(var(--jhlite-element-size) * 0.07);
  height: calc(var(--jhlite-element-size) * 0.4);
  width: calc(var(--jhlite-element-size) * 0.4);
  transform: translateX(0);
  transition:
    transform 0.3s ease,
    background-color 0.5s ease;
}

.jhlite-theme-switch_toggle-checked {
  transform: translateX(calc(var(--jhlite-element-size) * 0.6)) !important;
}
</style>
