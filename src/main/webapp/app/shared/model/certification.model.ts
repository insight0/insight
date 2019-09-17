export interface ICertification {
  id?: string;
  name?: string;
  year?: number;
  organization?: string;
  filePath?: string;
}

export class Certification implements ICertification {
  constructor(public id?: string, public name?: string, public year?: number, public organization?: string, public filePath?: string) {}
}
