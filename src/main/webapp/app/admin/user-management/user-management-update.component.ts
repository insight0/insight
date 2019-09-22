import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Gender, JhiLanguageHelper, User, UserService } from 'app/core';
import { IFunctionalRole } from 'app/shared/model/functional-role.model';
import { FunctionalRoleService } from 'app/entities/functional-role';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { filter, map, startWith } from 'rxjs/operators';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { Contract } from 'app/shared/model/contract.model';
import { SalaryPackage } from 'app/shared/model/salary-package.model';
import { Address } from 'app/shared/model/address.model';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html',
  styleUrls: ['./user-management.scss']
})
export class UserMgmtUpdateComponent implements OnInit {
  functionalRoles: IFunctionalRole[];
  skills: string[];
  allSkills: string[];
  allSkillsObjects: ISkill[];

  user: User;
  languages: any[];
  authorities: any[];
  isSaving: boolean;

  editForm = this.fb.group({
    login: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    yearsOfExperience: ['', [Validators.required, Validators.min(0), Validators.max(40)]],
    contractType: ['', [Validators.required]],
    contractDateDebut: ['', [Validators.required]],
    contractDateFin: ['', [Validators.required]],
    contractDescription: [],
    salaryRaw: ['', [Validators.required]],
    salaryNet: ['', [Validators.required]],
    salaryCNSS: ['', [Validators.required]],
    salaryRestoTicket: ['', [Validators.required]],
    salaryAssurance: ['', [Validators.required]],
    salaryTax: ['', [Validators.required]],
    functionalRoles: ['', [Validators.required]],
    leadershipSeniority: ['', [Validators.required]],
    technicalSeniority: ['', [Validators.required]],
    managementSeniority: ['', [Validators.required]],
    phoneNumber: ['', [Validators.required]],
    parentPhoneNumber: ['', [Validators.required]],
    parentPhoneOwner: ['', [Validators.required]],
    authorities: ['', [Validators.required]],
    email: ['', [Validators.required]],
    addressLine1: ['', [Validators.required]],
    addressLine2: [],
    addressCity: ['', [Validators.required]],
    addressCode: ['', [Validators.required]],
    skills: [],
    activated: [true],
    langKey: [],
    birthDate: ['', [Validators.required]],
    cnssAffiliateNumber: [],
    gender: ['', [Validators.required]],
    idCardNumber: ['', [Validators.required]]
  });

  private updateForm(user: User): void {
    if (user.functionalRoles && user.functionalRoles.length > 0) {
      this.user.roles = [];
      for (let role of user.functionalRoles) {
        this.user.roles.push(role.id);
      }
    }

    if (user.skills && user.skills.length > 0) {
      this.user.skillIds = [];
      for (let role of user.skills) {
        this.user.skillIds.push(role.name);
        this.skills = this.user.skillIds;
      }
    }

    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      firstName: user.firstName,
      lastName: user.lastName,
      title: user.title,
      email: user.email,
      activated: user.activated,
      langKey: user.langKey,
      authorities: user.authorities,
      managementSeniority: user.managementSeniority ? user.managementSeniority : 10,
      leadershipSeniority: user.leadershipSeniority ? user.leadershipSeniority : 10,
      technicalSeniority: user.technicalSeniority ? user.technicalSeniority : 10,
      yearsOfExperience: user.yearsOfExperience,
      contractType: user.contract[0].type,
      contractDateDebut: user.contract[0].startDate,
      contractDateFin: user.contract[0].endDate,
      contractDescription: user.contract[0].description,
      salaryRaw: user.salaryPackage.rawSalaryPerMonth,
      salaryNet: user.salaryPackage.netSalaryPerMonth,
      salaryCNSS: user.salaryPackage.nationalInsurancePerMonth,
      salaryRestoTicket: user.salaryPackage.mealVoucherPerMonth,
      salaryAssurance: user.salaryPackage.privateInsurancePerMonth,
      salaryTax: user.salaryPackage.taxPerMonth,
      functionalRoles: user.roles,
      phoneNumber: user.phoneNumber,
      parentPhoneNumber: user.parentPhoneNumber,
      parentPhoneOwner: user.parentPhoneOwner,
      addressLine1: user.address.line1,
      addressLine2: user.address.line2,
      addressCity: user.address.city,
      addressCode: user.address.postalCode,
      skills: user.skillIds,
      birthDate: user.birthDate,
      cnssAffiliateNumber: user.cnssAffiliateNumber,
      gender: user.gender,
      idCardNumber: user.idCardNumber
    });
  }

  private updateUser(user: User): void {
    user.login = this.editForm.get(['login']).value;
    user.firstName = this.editForm.get(['firstName']).value;
    user.lastName = this.editForm.get(['lastName']).value;
    user.email = this.editForm.get(['email']).value;
    user.activated = this.editForm.get(['activated']).value;
    user.langKey = this.editForm.get(['langKey']).value;
    user.authorities = this.editForm.get(['authorities']).value;
    user.title = this.editForm.get(['title']).value;
    user.yearsOfExperience = this.editForm.get(['yearsOfExperience']).value;
    user.managementSeniority = this.editForm.get(['managementSeniority']).value;
    user.leadershipSeniority = this.editForm.get(['leadershipSeniority']).value;
    user.technicalSeniority = this.editForm.get(['technicalSeniority']).value;
    user.contract[0].type = this.editForm.get(['contractType']).value;
    user.contract[0].startDate = this.editForm.get(['contractDateDebut']).value;
    user.contract[0].endDate = this.editForm.get(['contractDateFin']).value;
    user.contract[0].description = this.editForm.get(['contractDescription']).value;
    user.salaryPackage.netSalaryPerMonth = this.editForm.get(['salaryNet']).value;
    user.salaryPackage.rawSalaryPerMonth = this.editForm.get(['salaryRaw']).value;
    user.salaryPackage.nationalInsurancePerMonth = this.editForm.get(['salaryCNSS']).value;
    user.salaryPackage.mealVoucherPerMonth = this.editForm.get(['salaryRestoTicket']).value;
    user.salaryPackage.privateInsurancePerMonth = this.editForm.get(['salaryAssurance']).value;
    user.salaryPackage.taxPerMonth = this.editForm.get(['salaryTax']).value;
    user.phoneNumber = this.editForm.get(['phoneNumber']).value;
    user.parentPhoneNumber = this.editForm.get(['parentPhoneNumber']).value;
    user.parentPhoneOwner = this.editForm.get(['parentPhoneOwner']).value;
    user.address.line1 = this.editForm.get(['addressLine1']).value;
    user.address.line2 = this.editForm.get(['addressLine2']).value;
    user.address.city = this.editForm.get(['addressCity']).value;
    user.address.postalCode = this.editForm.get(['addressCode']).value;
    user.activated = true;
    user.langKey = 'fr';

    user.birthDate = this.editForm.get(['birthDate']).value;
    user.cnssAffiliateNumber = this.editForm.get(['cnssAffiliateNumber']).value;
    user.gender = this.editForm.get(['gender']).value;
    user.idCardNumber = this.editForm.get(['idCardNumber']).value;

    user.roles = this.editForm.get(['functionalRoles']).value;
    this.user.functionalRoles = [];
    for (let role of user.roles) {
      for (let objectRole of this.functionalRoles) {
        if (role === objectRole.id) {
          this.user.functionalRoles.push(objectRole);
        }
      }
    }

    this.user.skills = [];
    for (let role of this.skills) {
      this.user.skills.push({ name: role });
    }
  }

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  skillCtrl = new FormControl();
  filteredSkills: Observable<string[]>;
  @ViewChild('skillInput', { static: false }) skillInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto', { static: false }) matAutocomplete: MatAutocomplete;

  constructor(
    private languageHelper: JhiLanguageHelper,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    protected jhiAlertService: JhiAlertService,
    protected functionalRoleService: FunctionalRoleService,
    private fb: FormBuilder,
    protected skillService: SkillService
  ) {
    this.functionalRoles = [];
    this.skills = [];
  }

  add(event: MatChipInputEvent): void {
    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.skills.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.skillCtrl.setValue(null);
    }
  }

  remove(skill: string): void {
    const index = this.skills.indexOf(skill);

    if (index >= 0) {
      this.skills.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.skills.push(event.option.viewValue);
    this.skillInput.nativeElement.value = '';
    this.skillCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allSkills.filter(fruit => fruit.toLowerCase().indexOf(filterValue) === 0);
  }

  ngOnInit() {
    this.loadAll();
    this.isSaving = false;
    this.route.data.subscribe(({ user }) => {
      this.user = user.body ? user.body : user;

      if (!this.user) {
        this.user = new User();
      }

      if (!this.user.contract || this.user.contract.length < 1) {
        this.user.contract = new Array();
        this.user.contract.push(new Contract());
      }

      if (!this.user.salaryPackage) {
        this.user.salaryPackage = new SalaryPackage();
      }

      if (!this.user.address) {
        this.user.address = new Address();
      }

      this.updateForm(this.user);
    });
    this.authorities = [];
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });
    this.languageHelper.getAll().then(languages => {
      this.languages = languages;
    });
  }

  loadAll() {
    this.functionalRoleService
      .query({})
      .subscribe(
        (res: HttpResponse<IFunctionalRole[]>) => this.paginateFunctionalRoles(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.skillService
      .query()
      .pipe(
        filter((res: HttpResponse<ISkill[]>) => res.ok),
        map((res: HttpResponse<ISkill[]>) => res.body)
      )
      .subscribe(
        (res: ISkill[]) => {
          this.allSkillsObjects = res;
          this.allSkills = new Array();

          for (let skill of res) {
            if (this.user.skillIds.indexOf(skill.name) < 0) {
              this.allSkills.push(skill.name);
            }
          }

          this.filteredSkills = this.skillCtrl.valueChanges.pipe(
            startWith(null),
            map((fruit: string | null) => (fruit ? this._filter(fruit) : this.allSkills.slice()))
          );
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  protected paginateFunctionalRoles(data: IFunctionalRole[], headers: HttpHeaders) {
    for (let i = 0; i < data.length; i++) {
      this.functionalRoles.push(data[i]);
    }
  }

  previousState() {
    window.history.back();
  }

  formatLabel(value: number | null) {
    if (!value) {
      return 0;
    }

    /*
    console.log(this.management);

    if (value >= 0 && value <= 20) {
      return "1";
    }

    if (value > 20 && value <= 40) {
      return "2";
    }

    if (value > 40 && value <= 60) {
      return "3";
    }

    if (value > 60 && value <= 80) {
      return "4";
    }

    if (value > 80 && value <= 100) {
      return "5";
    }
*/

    return value;
  }

  save() {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== null) {
      this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    } else {
      this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }
  }

  private onSaveSuccess(result) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
