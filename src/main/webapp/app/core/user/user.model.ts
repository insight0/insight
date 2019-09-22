import { IContract } from 'app/shared/model/contract.model';
import { ISalaryPackage } from 'app/shared/model/salary-package.model';
import { IFunctionalRole } from 'app/shared/model/functional-role.model';
import { IAddress } from 'app/shared/model/address.model';
import { IDegree } from 'app/shared/model/degree.model';
import { ICertification } from 'app/shared/model/certification.model';
import { ISkill } from 'app/shared/model/skill.model';
import { IDocumentPlaceholder } from 'app/shared/model/document-placeholder.model';

export const enum Seniority {
  BEGINNER = 'BEGINNER',
  JUNIOR = 'JUNIOR',
  CONFIRMED = 'CONFIRMED',
  SENIOR = 'SENIOR',
  MASTER = 'MASTER'
}

export const enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

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
  address?: IAddress;
  phoneNumber?: string;
  parentPhoneNumber?: string;
  parentPhoneOwner?: string;
  managementSeniority?: number;
  leadershipSeniority?: number;
  technicalSeniority?: number;
  title?: string;
  degrees?: IDegree[];
  roles?: string[];
  skillIds?: string[];
  yearsOfExperience?: number;
  certifications?: ICertification[];
  skills?: ISkill[];
  idCard?: IDocumentPlaceholder;
  payslips?: IDocumentPlaceholder[];
  birthDate?: Date;
  cnssAffiliateNumber?: string;
  gender?: Gender;
  idCardNumber?: string;
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
    public functionalRoles?: IFunctionalRole[],
    public address?: IAddress,
    public phoneNumber?: string,
    public parentPhoneNumber?: string,
    public parentPhoneOwner?: string,
    public managementSeniority?: number,
    public leadershipSeniority?: number,
    public technicalSeniority?: number,
    public title?: string,
    public degrees?: IDegree[],
    public yearsOfExperience?: number,
    public certifications?: ICertification[],
    public skills?: ISkill[],
    public roles?: string[],
    public skillIds?: string[],
    public idCard?: IDocumentPlaceholder,
    public payslips?: IDocumentPlaceholder[],
    public birthDate?: Date,
    public cnssAffiliateNumber?: string,
    public gender?: Gender,
    public idCardNumber?: string
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
    this.address = address ? address : null;
  }
}
