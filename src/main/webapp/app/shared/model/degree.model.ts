export interface IDegree {
  id?: string;
  name?: string;
  year?: number;
  duration?: number;
  school?: string;
  filePath?: string;
}

export class Degree implements IDegree {
  constructor(
    public id?: string,
    public name?: string,
    public year?: number,
    public duration?: number,
    public school?: string,
    public filePath?: string
  ) {}
}
