import { RestModule } from '@/module/secondary/RestModule';

export interface RestCategory {
  name: string;
  modules: RestModule[];
}
