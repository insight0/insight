export interface IFunctionalRole {
  id?: string;
  name?: string;
  description?: string;
  maxHolidays?: number;
  workingHours?: number;
  operations?: boolean;
}

export class FunctionalRole implements IFunctionalRole {
  constructor(
    public id?: string,
    public name?: string,
    public description?: string,
    public maxHolidays?: number,
    public workingHours?: number,
    public operations?: boolean
  ) {}
}
