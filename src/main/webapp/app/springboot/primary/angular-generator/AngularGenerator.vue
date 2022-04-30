<template>
  <div id="v-pills-angular" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-settings-tab">
    <DefaultButtonVue
      id="angular"
      :label="'Generate Angular'"
      :data-selector="selectorPrefix + '.add-angular-button'"
      @click.prevent="addAngular"
    />
  </div>
</template>

<script lang="ts" src="./AngularGenerator.component.ts"></script>
