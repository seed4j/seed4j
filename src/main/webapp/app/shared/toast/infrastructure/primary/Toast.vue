<template>
  <div v-if="show" class="jhlite-toast-overlay" data-testid="toast-overlay">
    <div
      ref="toast"
      :class="{ '-success': type === 'SUCCESS', '-error': type === 'ERROR' }"
      class="jhlite-toast"
      role="alert"
      aria-live="assertive"
      aria-atomic="true"
    >
      <div class="jhlite-toast--message">{{ message }}</div>
      <button data-testid="toast.close" type="button" class="jhlite-toast--action" title="Close" @click="close">
        <IconVue name="cancel" />
      </button>
    </div>
  </div>
</template>

<script lang="ts" src="./Toast.component.ts"></script>
