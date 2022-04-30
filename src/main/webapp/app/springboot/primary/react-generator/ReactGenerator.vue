<template>
  <div id="v-pills-react" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-react-tab">
    <div class="list-group--inline py-2">
      <label for="react-with-style" class="list-group-item gap-3">
        <input
          id="react-with-style"
          v-model="isReactWithStyle"
          name="react-with-style"
          class="form-check-input flex-shrink-0"
          type="checkbox"
          value=""
        />
        <span class="form-checked-content">
          <strong>Add React style</strong>
        </span>
      </label>
    </div>
    <DefaultButtonVue
      id="react"
      :label="'Generate React'"
      :data-selector="selectorPrefix + '.add-react-button'"
      @click.prevent="addReact"
    />
  </div>
</template>

<script lang="ts" src="./ReactGenerator.component.ts"></script>
