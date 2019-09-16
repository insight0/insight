export interface ISalaryPackage {
  id?: string;
  netSalaryPerMonth?: number;
  nationalInsurancePerMonth?: number;
  taxPerMonth?: number;
  privateInsurancePerMonth?: number;
  mealVoucherPerMonth?: number;
  bonusPerYear?: number;
}

export class SalaryPackage implements ISalaryPackage {
  constructor(
    public id?: string,
    public netSalaryPerMonth?: number,
    public nationalInsurancePerMonth?: number,
    public taxPerMonth?: number,
    public privateInsurancePerMonth?: number,
    public mealVoucherPerMonth?: number,
    public bonusPerYear?: number
  ) {}
}
