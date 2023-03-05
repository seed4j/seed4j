import { LandscapeSelectionElement } from '@/module/domain/landscape/LandscapeSelectionElement';
import { LandscapeSelectionTree } from '@/module/domain/landscape/LandscapeSelectionTree';
import { applicationBaseNamePropertyDefinition, moduleSlug, optionalBooleanPropertyDefinition } from '../Modules.fixture';
import { defaultLandscape, featureSlug } from './Landscape.fixture';
import { describe, it, expect } from 'vitest';

describe('Landscape', () => {
  describe('Reset applied modules', () => {
    it('Should ignore unknown modules', () => {
      const landscape = defaultLandscape().resetAppliedModules([moduleSlug('init'), moduleSlug('unknown')]);

      expect(landscape.isSelected(moduleSlug('init'))).toBe(true);
      expect(landscape.isApplied(moduleSlug('init'))).toBe(true);
      expect(landscape.isSelected(moduleSlug('unknown'))).toBe(false);
      expect(landscape.isApplied(moduleSlug('unknown'))).toBe(false);
    });

    it('Should be applied for newly applied module', () => {
      const landscape = defaultLandscape().resetAppliedModules([moduleSlug('init')]);

      expect(landscape.isApplied(moduleSlug('init'))).toBe(true);
      expect(landscape.isSelected(moduleSlug('init'))).toBe(true);

      expect(landscape.notAppliedSelectedModules()).toEqual([]);
      expect(landscape.selectedModules()).toEqual([moduleSlug('init')]);
    });

    it('Should not be applied for previously applied module', () => {
      const landscape = defaultLandscape()
        .resetAppliedModules([moduleSlug('init')])
        .resetAppliedModules([moduleSlug('vue')]);

      expect(landscape.isApplied(moduleSlug('init'))).toBe(false);
      expect(landscape.isSelected(moduleSlug('init'))).toBe(true);

      expect(landscape.isApplied(moduleSlug('vue'))).toBe(true);
      expect(landscape.isSelected(moduleSlug('vue'))).toBe(true);
    });
  });

  describe('Apply modules', () => {
    it('Should ignore unknown modules', () => {
      const landscape = defaultLandscape().appliedModules([moduleSlug('unknown')]);

      expect(landscape.isApplied(moduleSlug('unknown'))).toBe(false);
    });

    it('Should mark applied modules as applied', () => {
      const landscape = defaultLandscape()
        .resetAppliedModules([moduleSlug('init')])
        .appliedModules([moduleSlug('vue')]);

      expect(landscape.isApplied(moduleSlug('init'))).toBe(true);
      expect(landscape.isSelected(moduleSlug('init'))).toBe(true);

      expect(landscape.isApplied(moduleSlug('vue'))).toBe(true);
      expect(landscape.isSelected(moduleSlug('vue'))).toBe(true);
    });
  });

  describe('Selection', () => {
    it('Should not select unknown module', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('unknown'));

      expect(landscape.isSelected(moduleSlug('unknown'))).toBe(false);
    });

    it('Should not select not selectable module', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('java-base'));

      expect(landscape.isSelected(moduleSlug('java-base'))).toBe(false);
    });

    it('Should select modules', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('vue'));

      expect(landscape.isSelected(moduleSlug('init'))).toBe(true);
      expect(landscape.isSelected(moduleSlug('vue'))).toBe(true);

      expect(landscape.isSelected(featureSlug('client'))).toBe(true);

      expect(landscape.notAppliedSelectedModules()).toEqual([moduleSlug('vue'), moduleSlug('init')]);
      expect(landscape.selectedModules()).toEqual([moduleSlug('vue'), moduleSlug('init')]);
    });

    it('Should not select features', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('maven')).toggle(moduleSlug('java-base'));

      expect(landscape.selectedModules()).not.toContainEqual(moduleSlug('java-build-tools'));
    });

    it('Should switch selected module in feature', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('maven')).toggle(moduleSlug('gradle'));

      expect(landscape.isSelected(moduleSlug('gradle'))).toBe(true);
      expect(landscape.isSelected(moduleSlug('maven'))).toBe(false);
    });
  });

  describe('Unselection', () => {
    it('Should not unselect unknown module', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('unknown'));

      expect(landscape.isSelected(moduleSlug('unknown'))).toBe(false);
    });

    it('Should unselect selected module', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('maven')).toggle(moduleSlug('java-base')).toggle(moduleSlug('maven'));

      expect(landscape.isSelected(moduleSlug('maven'))).toBe(false);
      expect(landscape.isSelected(moduleSlug('java-base'))).toBe(false);
      expect(landscape.isSelected(moduleSlug('init'))).toBe(true);
    });
  });

  describe('Selection tree', () => {
    it('Should get empty selection tree for unknown module', () => {
      const selectionTree = defaultLandscape().selectionTreeFor(moduleSlug('unknown'));

      expect(selectionTree).toEqual(LandscapeSelectionTree.EMPTY);
    });

    it('Should get empty selection tree for root module', () => {
      const selectionTree = defaultLandscape().selectionTreeFor(moduleSlug('init'));

      expect(selectionTree.elements).toEqual([selectableModule('init')]);
    });

    it('Should get selection tree with one selectable module', () => {
      const selectionTree = defaultLandscape().selectionTreeFor(moduleSlug('vue'));

      expect(selectionTree.elements).toEqual([selectableModule('vue'), selectableModule('init')]);
      expect(selectionTree.isSelectable()).toBe(true);
    });

    it('Should get selection tree with one not selectable feature', () => {
      const selectionTree = defaultLandscape().selectionTreeFor(moduleSlug('java-base'));

      expect(selectionTree.elements).toEqual([notSelectableModule('java-base'), notSelectableFeature('java-build-tools')]);
      expect(selectionTree.isSelectable()).toBe(false);
    });

    it('Should get selection tree with selected module', () => {
      const selectionTree = defaultLandscape().toggle(moduleSlug('maven')).selectionTreeFor(moduleSlug('java-base'));

      expect(selectionTree.elements).toEqual([
        selectableModule('java-base'),
        selectableFeature('java-build-tools'),
        selectableModule('maven'),
        selectableModule('init'),
      ]);
      expect(selectionTree.isSelectable()).toBe(true);
    });

    it('Should get selection tree with dependency to feature with one module', () => {
      const selectionTree = defaultLandscape().toggle(moduleSlug('maven')).selectionTreeFor(moduleSlug('liquibase'));

      expect(selectionTree.elements).toContainEqual(selectableFeature('jpa'));
      expect(selectionTree.elements).toContainEqual(selectableModule('postgresql'));
      expect(selectionTree.elements).toContainEqual(selectableModule('spring-boot'));
      expect(selectionTree.isSelectable()).toBe(true);
    });

    it('Should get selection tree with dependency to feature with incompatible selected module', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('maven')).toggle(moduleSlug('gitlab-maven'));

      expect(landscape.isSelectable(moduleSlug('maven'))).toBe(true);
      expect(landscape.isSelectable(moduleSlug('gradle'))).toBe(false);
      expect(landscape.isSelectable(moduleSlug('gitlab-gradle'))).toBe(false);

      const gradleSelectionTree = landscape.selectionTreeFor(moduleSlug('gradle'));
      expect(gradleSelectionTree.elements).toContainEqual(notSelectableModule('gradle'));
      expect(gradleSelectionTree.isSelectable()).toBe(false);

      const gitlabGradleSelectionTree = landscape.selectionTreeFor(moduleSlug('gitlab-gradle'));
      expect(gitlabGradleSelectionTree.elements).toContainEqual(notSelectableModule('maven'));
      expect(gitlabGradleSelectionTree.elements).toContainEqual(notSelectableModule('gradle'));
      expect(gitlabGradleSelectionTree.isSelectable()).toBe(false);
    });

    it('Should get selection tree with disabled incompatible module dependencies', () => {
      const landscape = defaultLandscape().toggle(moduleSlug('maven'));
      const selectionTree = landscape.selectionTreeFor(moduleSlug('gradle'));

      expect(landscape.isSelectable(moduleSlug('gitlab-gradle'))).toBe(false);

      expect(selectionTree.elements).toContainEqual(selectableModule('gradle'));
      expect(selectionTree.isSelectable()).toBe(true);
    });

    it('Should get not selectable feature for applied module in feature', () => {
      const landscape = defaultLandscape().appliedModules([moduleSlug('maven')]);
      const selectionTree = landscape.selectionTreeFor(moduleSlug('gradle'));

      expect(landscape.isSelectable(moduleSlug('gradle'))).toBe(false);

      expect(selectionTree.elements).toContainEqual(notSelectableModule('gradle'));
      expect(selectionTree.isSelectable()).toBe(false);
    });
  });

  describe('Unselection tree', () => {
    it('Should get empty unselection tree for unknown module', () => {
      const unselectionTree = defaultLandscape().unselectionTreeFor(moduleSlug('unknown'));

      expect(unselectionTree.elements).toEqual([]);
    });

    it('Should get empty unselection tree for not selected module', () => {
      const unselectionTree = defaultLandscape().unselectionTreeFor(moduleSlug('init'));

      expect(unselectionTree.elements).toEqual([]);
    });

    it('Should get unselection tree for single selected module', () => {
      const unselectionTree = defaultLandscape().toggle(moduleSlug('init')).unselectionTreeFor(moduleSlug('init'));

      expect(unselectionTree.elements).toEqual([moduleSlug('init')]);
    });

    it('Should get unselection of other selected module in feature', () => {
      const unselectionTree = defaultLandscape()
        .toggle(moduleSlug('maven'))
        .toggle(moduleSlug('jpa'))
        .unselectionTreeFor(moduleSlug('gradle'));

      expect(unselectionTree.elements).toEqual([moduleSlug('maven')]);
    });

    it('Should get unselection tree of module not in a feature', () => {
      const unselectionTree = defaultLandscape()
        .toggle(moduleSlug('maven'))
        .toggle(moduleSlug('spring-boot'))
        .unselectionTreeFor(moduleSlug('init'));

      expect(unselectionTree.elements).toEqual([
        moduleSlug('init'),
        moduleSlug('maven'),
        featureSlug('java-build-tools'),
        moduleSlug('spring-boot'),
      ]);
    });

    it('Should get unselection tree of selected module in feature', () => {
      const unselectionTree = defaultLandscape()
        .toggle(moduleSlug('maven'))
        .toggle(moduleSlug('postgresql'))
        .unselectionTreeFor(moduleSlug('maven'));

      expect(unselectionTree.elements).toEqual([
        moduleSlug('maven'),
        featureSlug('java-build-tools'),
        moduleSlug('spring-boot'),
        moduleSlug('postgresql'),
        featureSlug('jpa'),
      ]);
    });
  });

  describe('Selectable', () => {
    it('Should not be selectable for unknown module', () => {
      expect(defaultLandscape().isSelectable(moduleSlug('unknown'))).toBe(false);
    });

    it('Should be selectable for selectable module', () => {
      expect(defaultLandscape().isSelectable(moduleSlug('init'))).toBe(true);
    });

    it('Should not be selectable for not selectable module', () => {
      expect(defaultLandscape().isSelectable(moduleSlug('java-base'))).toBe(false);
    });
  });

  describe('Selected module properties', () => {
    it('Should not have any selected properties without selected module', () => {
      expect(defaultLandscape().selectedModulesProperties()).toEqual([]);
    });

    it('Should get selected module properties', () => {
      const properties = defaultLandscape()
        .toggle(moduleSlug('init'))
        .toggle(moduleSlug('infinitest'))
        .toggle(moduleSlug('maven'))
        .selectedModulesProperties();

      expect(properties).toEqual([applicationBaseNamePropertyDefinition(), optionalBooleanPropertyDefinition()]);
    });
  });
});

const selectableModule = (slug: string): LandscapeSelectionElement => ({ slug: moduleSlug(slug), selectable: true });
const notSelectableModule = (slug: string): LandscapeSelectionElement => ({ slug: moduleSlug(slug), selectable: false });

const selectableFeature = (slug: string): LandscapeSelectionElement => ({ slug: featureSlug(slug), selectable: true });
const notSelectableFeature = (slug: string): LandscapeSelectionElement => ({ slug: featureSlug(slug), selectable: false });
