type ProjectFilename = string;

export interface Project {
  filename: ProjectFilename;
  content: ArrayBuffer;
}
