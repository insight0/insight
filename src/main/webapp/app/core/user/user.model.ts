import { IContract } from 'app/shared/model/contract.model';
import { ISalaryPackage } from 'app/shared/model/salary-package.model';
import { IFunctionalRole } from 'app/shared/model/functional-role.model';

export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  contract?: IContract[];
  salaryPackage?: ISalaryPackage;
  functionalRoles?: IFunctionalRole[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: any[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string,
    public contract?: IContract[],
    public salaryPackage?: ISalaryPackage,
    public functionalRoles?: IFunctionalRole[]
  ) {
    this.id = id ? id : null;
    this.login = login ? login : null;
    this.firstName = firstName ? firstName : null;
    this.lastName = lastName ? lastName : null;
    this.email = email ? email : null;
    this.activated = activated ? activated : false;
    this.langKey = langKey ? langKey : null;
    this.authorities = authorities ? authorities : null;
    this.createdBy = createdBy ? createdBy : null;
    this.createdDate = createdDate ? createdDate : null;
    this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
    this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
    this.password = password ? password : null;
    this.contract = contract ? contract : null;
    this.salaryPackage = salaryPackage ? salaryPackage : null;
    this.functionalRoles = functionalRoles ? functionalRoles : null;
  }
}
