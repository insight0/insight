export const enum ContractType {
  SIVP = 'SIVP',
  KARAMA = 'KARAMA',
  CDD = 'CDD',
  CDI = 'CDI',
  SUMMER_TRAINEESHIP = 'SUMMER_TRAINEESHIP',
  FINAL_YEAR_TRAINEESHIP = 'FINAL_YEAR_TRAINEESHIP',
  FREELANCER = 'FREELANCER'
}

export interface IContact {
  id?: string;
  firstName?: string;
  lastName?: string;
  role?: string;
  phoneNumber?: string;
  email?: string;
  organisationId?: string;
  type?: ContractType;
}

export class Contact implements IContact {
  constructor(
    public id?: string,
    public firstName?: string,
    public lastName?: string,
    public role?: string,
    public phoneNumber?: string,
    public email?: string,
    public organisationId?: string,
    public type?: ContractType
  ) {}
}
