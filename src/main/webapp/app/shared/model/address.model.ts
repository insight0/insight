export interface IAddress {
  id?: string;
  line1?: string;
  line2?: string;
  city?: string;
  postalCode?: string;
  country?: string;
  ltd?: number;
  lgt?: number;
}

export class Address implements IAddress {
  constructor(
    public id?: string,
    public line1?: string,
    public line2?: string,
    public city?: string,
    public postalCode?: string,
    public country?: string,
    public ltd?: number,
    public lgt?: number
  ) {}
}
