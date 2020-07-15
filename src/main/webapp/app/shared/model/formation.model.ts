export interface IFormation {
  id?: string;
  title?: string;
  description?: string;
  society?: string;
  location?: string;
  candidate?: string;
  concerned?: string;
  date?: Date;
}

export class Formation implements IFormation {
  constructor(
    public id?: string,
    public title?: string,
    public description?: string,
    public society?: string,
    public location?: string,
    public candidate?: string,
    public concerned?: string,
    public date?: Date
  ) {}
}
