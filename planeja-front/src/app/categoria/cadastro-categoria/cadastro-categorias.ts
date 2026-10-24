import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CategoriaService } from '../categoria-service';
import { DadosCategoriaForm, DetalhesCategoria } from '../categoria-dados';
import { ValidationErrorResponse } from '../../common/validation/validation-error-model';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { Header } from '../../common/components/header/header';

interface CadastroCategoriaForm{
  nome: FormControl<string>;
}

@Component({
  selector: 'app-cadastro-categoria',
  imports: [ReactiveFormsModule, CommonModule, RouterModule, Header],
  templateUrl: './cadastro-categorias.html',
  styleUrl: './cadastro-categorias.scss',
})
export class CadastroCategorias implements OnInit {
  service: CategoriaService = inject(CategoriaService);
  rotaAtiva: ActivatedRoute = inject(ActivatedRoute);
  router = inject(Router);
  toast: ToastrService = inject(ToastrService);
  form!: FormGroup<CadastroCategoriaForm>;
  idCategoriaEdicao?: string | null = null;

  ngOnInit(): void {
    this.form = new FormGroup<CadastroCategoriaForm>({
      nome: new FormControl('', { nonNullable: true, validators: Validators.required })
    });

    this.carregarDadosEdicao();
  }

  carregarDadosEdicao() {
    this.idCategoriaEdicao = this.rotaAtiva.snapshot.queryParamMap.get('id') ?? null;

    if (!this.idCategoriaEdicao) {
      return;
    }

    this.service.obterPorId(this.idCategoriaEdicao).subscribe({
      next: (categoria) => {
        this.form.patchValue({
          nome: categoria.nome,
        });
      },
      error: () => this.toast.error('Erro ao carregar dados do cartão'),
    });
  }

  isFormInvalid(): boolean {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.toast.warning('Erro Lógico, Verifique os valores informados');
      return true;
    }
    return false;
  }

  handleSubmit(): void {
    if (this.isFormInvalid()) {
      return;
    }

    const dadosCategoria: DadosCategoriaForm = this.form.value as DadosCategoriaForm;

    const requisicao: Observable<DetalhesCategoria | void> = this.idCategoriaEdicao
      ? this.service.atualizarCategoria(this.idCategoriaEdicao, dadosCategoria)
      : this.service.criarCategoria(dadosCategoria);

    requisicao.subscribe({
      next: (response) => {
        this.toast.success('Categoria cadastrado com sucesso!');
        this.form.reset();
        this.idCategoriaEdicao = null;
      },
      error: (error: any) => {
        this.onApiError(error);
      },
    });
  }

  cancelar() {
    this.form.reset();
    this.router.navigate(['/listar-categorias']);
  }

  private aplicarErrorValidacao(error: ValidationErrorResponse) {
    error.camposInvalidos.forEach((invalido) => {
      console.log('campo invalido', invalido);
      const control = this.form.get(invalido.campo);
      console.log('control', control);
      if (control) {
        control.setErrors({ apiError: invalido.mensagem });
        control.markAsTouched();
      }
    });
  }

  private onApiError(response: any) {
    if (response.status === 422) {
      this.aplicarErrorValidacao(response.error);
      this.toast.error('Erro de validação verificque');
      return;
    }
  }
}
