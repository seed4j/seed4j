export interface ManagementInfo {
  git: GitManagementInfo;
}

export interface GitManagementInfo {
  commit: CommitManagementInfo;
  branch: string;
  build: BuildManagementInfo;
}

export interface CommitManagementInfo {
  id: IdManagementInfo;
}

export interface IdManagementInfo {
  describe: string;
  abbrev: string;
}

export interface BuildManagementInfo {
  version: string;
  time: string;
}
