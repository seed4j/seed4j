import { GLOBAL_WINDOW, provide } from '@/injections';
import { MODULES_REPOSITORY } from '@/module/application/ModuleProvider';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { ProjectActionsVue } from '@/module/primary/project-actions';
import { ALERT_BUS } from '@/shared/alert/application/AlertProvider';
import { VueWrapper, flushPromises, shallowMount } from '@vue/test-utils';
import { describe, expect, it, vi } from 'vitest';
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
  describe('Download', () => {
    it('should disable download without project path', async () => {
      const wrapper = wrap({ folderPath: '' });

      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeDefined();
    });

    it('should download file using repository', async () => {
      const link = stubLink();
      windowStub.document.createElement.mockReturnValue(link);

      const modules = stubModulesRepository();
      modules.download.mockResolvedValue(defaultProject());
      const wrapper = wrap({ modules });

      wrapper.find(wrappedElement('download-button')).trigger('click');
      await flushForm(wrapper);
      await flushPromises();

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeUndefined();
      expect(windowStub.URL.createObjectURL).toHaveBeenCalledOnce();
      expect(windowStub.document.createElement).toHaveBeenCalledOnce();
      expect(windowStub.document.body.appendChild).toHaveBeenCalledOnce();
      expect(windowStub.URL.revokeObjectURL).toHaveBeenCalledOnce();
      expect(windowStub.document.body.removeChild).toHaveBeenCalledOnce();

      expect(link.download).toBe('seed4j.zip');
      expect(modules.download).toHaveBeenCalledOnce();
    });

    it('should handle download errors', async () => {
      const modules = stubModulesRepository();
      modules.download.mockRejectedValue(new Error("Project can't be downloaded"));
      const wrapper = wrap({ modules });

      wrapper.find(wrappedElement('download-button')).trigger('click');
      await flushForm(wrapper);

      await flushPromises();

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeUndefined();
      expect(modules.download).toHaveBeenCalledOnce();

      expect(alertBus.error).toHaveBeenLastCalledWith("Project can't be downloaded");
    });
  });
});

const flushForm = async (wrapper: VueWrapper) => {
  await wrapper.vm.$nextTick();
};
