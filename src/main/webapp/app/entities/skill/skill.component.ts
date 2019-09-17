import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISkill } from 'app/shared/model/skill.model';
import { AccountService } from 'app/core';
import { SkillService } from './skill.service';

@Component({
  selector: 'jhi-skill',
  templateUrl: './skill.component.html'
})
export class SkillComponent implements OnInit, OnDestroy {
  skills: ISkill[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected skillService: SkillService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.skillService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ISkill[]>) => res.ok),
          map((res: HttpResponse<ISkill[]>) => res.body)
        )
        .subscribe((res: ISkill[]) => (this.skills = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.skillService
      .query()
      .pipe(
        filter((res: HttpResponse<ISkill[]>) => res.ok),
        map((res: HttpResponse<ISkill[]>) => res.body)
      )
      .subscribe(
        (res: ISkill[]) => {
          this.skills = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSkills();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISkill) {
    return item.id;
  }

  registerChangeInSkills() {
    this.eventSubscriber = this.eventManager.subscribe('skillListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
