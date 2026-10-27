import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModuleSlug } from '@/module/domain/ModuleSlug';

type ModuleDescription = string;
type ModuleTag = string;

export interface Module {
  slug: ModuleSlug;
  description: ModuleDescription;
  properties: ModulePropertyDefinition[];
  tags: ModuleTag[];
}
