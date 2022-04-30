<template>
  <div id="v-pills-svelte" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-svelte-tab">
    <div class="list-group--inline py-2">
      <label for="svelte-with-style" class="list-group-item gap-3">
        <input
          id="svelte-with-style"
          v-model="isSvelteWithStyle"
          type="checkbox"
          name="svelte-with-style"
          class="form-check-input flex-shrink-0"
          value=""
        />
        <span class="form-checked-content">
          <strong>Add Svelte style</strong>
        </span>
      </label>
    </div>
    <DefaultButtonVue
      id="svelte"
      :label="'Generate Svelte (not implemented)'"
      :data-selector="selectorPrefix + '.add-svelte-button'"
      @click.prevent=""
    />
  </div>
</template>

<script lang="ts" src="./SvelteGenerator.component.ts"></script>
