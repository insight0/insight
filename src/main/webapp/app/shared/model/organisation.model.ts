export interface IOrganisation {
  id?: string;
  name?: string;
  phoneNumber?: string;
  siteUrl?: string;
  logoUrl?: string;
  email?: string;
}

export class Organisation implements IOrganisation {
  constructor(
    public id?: string,
    public name?: string,
    public phoneNumber?: string,
    public siteUrl?: string,
    public logoUrl?: string,
    public email?: string
  ) {}
}
