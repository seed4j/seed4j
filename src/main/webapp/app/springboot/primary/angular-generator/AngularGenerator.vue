<template>
  <div id="v-pills-angular" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-settings-tab">
    <div class="list-group--inline py-2">
      <label for="angular-with-style" class="list-group-item gap-3">
        <input
          id="angular-with-style"
          v-model="isAngularWithStyle"
          type="checkbox"
          name="angular-with-style"
          class="form-check-input flex-shrink-0"
        />
        <span class="form-checked-content">
          <strong>Add Angular style</strong>
        </span>
      </label>
    </div>
    <button
      id="angular"
      class="btn btn-outline-primary"
      :data-selector="selectorPrefix + '.add-angular-button'"
      @click.prevent="addAngular"
    >
      Generate Angular
    </button>
  </div>
</template>

<script lang="ts" src="./AngularGenerator.component.ts"></script>
