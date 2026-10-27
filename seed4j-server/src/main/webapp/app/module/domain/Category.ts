import { Module } from '@/module/domain/Module';

type CategoryName = string;

export interface Category {
  name: CategoryName;
  modules: Module[];
}
