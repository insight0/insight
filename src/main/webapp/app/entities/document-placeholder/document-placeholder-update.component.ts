import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDocumentPlaceholder, DocumentPlaceholder } from 'app/shared/model/document-placeholder.model';
import { DocumentPlaceholderService } from './document-placeholder.service';

@Component({
  selector: 'jhi-document-placeholder-update',
  templateUrl: './document-placeholder-update.component.html'
})
export class DocumentPlaceholderUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    version: [null, [Validators.required]],
    url: [null, [Validators.required]],
    type: [null, [Validators.required]],
    size: [null, [Validators.required]]
  });

  constructor(
    protected documentPlaceholderService: DocumentPlaceholderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ documentPlaceholder }) => {
      this.updateForm(documentPlaceholder);
    });
  }

  updateForm(documentPlaceholder: IDocumentPlaceholder) {
    this.editForm.patchValue({
      id: documentPlaceholder.id,
      name: documentPlaceholder.name,
      description: documentPlaceholder.description,
      version: documentPlaceholder.version,
      url: documentPlaceholder.url,
      type: documentPlaceholder.type,
      size: documentPlaceholder.size
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const documentPlaceholder = this.createFromForm();
    if (documentPlaceholder.id !== undefined) {
      this.subscribeToSaveResponse(this.documentPlaceholderService.update(documentPlaceholder));
    } else {
      this.subscribeToSaveResponse(this.documentPlaceholderService.create(documentPlaceholder));
    }
  }

  private createFromForm(): IDocumentPlaceholder {
    return {
      ...new DocumentPlaceholder(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      version: this.editForm.get(['version']).value,
      url: this.editForm.get(['url']).value,
      type: this.editForm.get(['type']).value,
      size: this.editForm.get(['size']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentPlaceholder>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
