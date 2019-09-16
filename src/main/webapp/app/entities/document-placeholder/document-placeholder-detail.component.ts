import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentPlaceholder } from 'app/shared/model/document-placeholder.model';

@Component({
  selector: 'jhi-document-placeholder-detail',
  templateUrl: './document-placeholder-detail.component.html'
})
export class DocumentPlaceholderDetailComponent implements OnInit {
  documentPlaceholder: IDocumentPlaceholder;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documentPlaceholder }) => {
      this.documentPlaceholder = documentPlaceholder;
    });
  }

  previousState() {
    window.history.back();
  }
}
