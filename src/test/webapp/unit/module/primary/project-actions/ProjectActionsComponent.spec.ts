import { flushPromises, shallowMount, VueWrapper } from '@vue/test-utils';
import { describe, expect, it, vi } from 'vitest';

import { GLOBAL_WINDOW, provide } from '@/injections';
import { MODULES_REPOSITORY } from '@/module/application/ModuleProvider';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { ProjectActionsVue } from '@/module/primary/project-actions';
import { ALERT_BUS } from '@/shared/alert/application/AlertProvider';

import { stubAlertBus } from '../../../shared/alert/domain/AlertBus.fixture';
import { wrappedElement } from '../../../WrappedElement';
import { defaultProject, stubModulesRepository } from '../../domain/Modules.fixture';
import { stubWindow } from '../GlobalWindow.fixture';

interface WrapperOptions {
  folderPath: string;
  isPrettierButtonEnabled: boolean;
  modules: ModulesRepository;
}

const alertBus = stubAlertBus();
const windowStub = stubWindow();

const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
  const { modules, folderPath, isPrettierButtonEnabled }: WrapperOptions = {
    folderPath: '/dummy',
    modules: stubModulesRepository(),
    isPrettierButtonEnabled: true,
    ...options,
  };

  provide(MODULES_REPOSITORY, modules);
  provide(ALERT_BUS, alertBus);
  provide(GLOBAL_WINDOW, windowStub);

  return shallowMount(ProjectActionsVue, {
    props: {
      folderPath,
      isPrettierButtonEnabled,
    },
  });
};

const stubLink = () => ({
  href: '',
  click: vi.fn(),
  download: '',
});

describe('Project actions', () => {
  describe('Formatting', () => {
    it('should disable formatting without project path', async () => {
      const wrapper = wrap({ folderPath: '' });

      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('format-button')).attributes('disabled')).toBeDefined();
    });

    it('should disable format button according to prop', () => {
      const wrapper = wrap({ isPrettierButtonEnabled: false });

      expect(wrapper.find(wrappedElement('format-button')).attributes('disabled')).toBeDefined();
    });

    it('should enable format button according to prop', () => {
      const wrapper = wrap({ isPrettierButtonEnabled: true });

      expect(wrapper.find(wrappedElement('format-button')).attributes('disabled')).toBeUndefined();
    });
  });

  it('should format file using repository', async () => {
    const modules = stubModulesRepository();
    modules.format.resolves(defaultProject());
    const wrapper = wrap({ modules });

    wrapper.find(wrappedElement('format-button')).trigger('click');
    await flushForm(wrapper);
    await flushPromises();

    expect(wrapper.find(wrappedElement('format-button')).attributes('disabled')).toBeUndefined();
    expect(modules.format.callCount).toBe(1);

    const [message] = alertBus.success.lastCall.args;
    expect(message).toBe('Project formatted');
  });

  it('should handle formatting errors', async () => {
    const modules = stubModulesRepository();
    modules.format.rejects();
    const wrapper = wrap({ modules });

    wrapper.find(wrappedElement('format-button')).trigger('click');
    await flushForm(wrapper);
    await flushPromises();

    expect(wrapper.find(wrappedElement('format-button')).attributes('disabled')).toBeUndefined();
    expect(modules.format.callCount).toBe(1);

    const [message] = alertBus.error.lastCall.args;
    expect(message).toBe("Project can't be formatted");
  });

  describe('Download', () => {
    it('should disable download without project path', async () => {
      const wrapper = wrap({ folderPath: '' });

      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeDefined();
    });

    it('should download file using repository', async () => {
      const link = stubLink();
      windowStub.document.createElement.returns(link);

      const modules = stubModulesRepository();
      modules.download.resolves(defaultProject());
      const wrapper = wrap({ modules });

      wrapper.find(wrappedElement('download-button')).trigger('click');
      await flushForm(wrapper);
      await flushPromises();

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeUndefined();
      expect(windowStub.URL.createObjectURL.calledOnce).toBeTruthy();
      expect(windowStub.document.createElement.calledOnce).toBeTruthy();
      expect(windowStub.document.body.appendChild.calledOnce).toBeTruthy();
      expect(windowStub.URL.revokeObjectURL.calledOnce).toBeTruthy();
      expect(windowStub.document.body.removeChild.calledOnce).toBeTruthy();

      expect(link.download).toBe('jhipster.zip');
      expect(modules.download.callCount).toBe(1);
    });

    it('should handle download errors', async () => {
      const modules = stubModulesRepository();
      modules.download.rejects();
      const wrapper = wrap({ modules });

      wrapper.find(wrappedElement('download-button')).trigger('click');
      await flushForm(wrapper);

      await flushPromises();

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeUndefined();
      expect(modules.download.callCount).toBe(1);

      const [message] = alertBus.error.lastCall.args;
      expect(message).toBe("Project can't be downloaded");
    });
  });
});

const flushForm = async (wrapper: VueWrapper) => {
  await wrapper.vm.$nextTick();
};
