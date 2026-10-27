import { Component, inject, OnInit} from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { DadosLancamentoForm, TipoLancamento } from '../lancamento-dados';
import { ToastrService } from 'ngx-toastr';
import { LancamentoService } from '../lancamento-service';
import { DetalhesCategoria } from '../../categoria/categoria-dados';
import { DadosCartaoForm, DetalhesCartao } from '../../cartoes/cartao-dados';
import { forkJoin } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Header } from '../../common/components/header/header';
import { NgxMaskDirective } from 'ngx-mask';
import { ValidationErrorResponse } from '../../common/validation/validation-error-model';

interface CadastroLancamentoForm {
  categoriaId: FormControl<number>;
  descricao: FormControl<string>;
  dataLancamento: FormControl<string>;
  valor: FormControl<string>;
  tipoLancamento: FormControl<TipoLancamento | ''>;
  cartaoId: FormControl<number>;
}

@Component({
  selector: 'app-cadastro-lancamento',
  imports: [Header, ReactiveFormsModule, CommonModule, NgxMaskDirective],
  templateUrl: './cadastro-lancamento.html',
  styleUrl: './cadastro-lancamento.scss',
})
export class CadastroLancamento implements OnInit {
  form!: FormGroup<CadastroLancamentoForm>;

  toast = inject(ToastrService);
  service = inject(LancamentoService);
  categoriasAtivas: DetalhesCategoria[] = [];
  cartoesAtivos: DetalhesCartao[] = [];

  ngOnInit(): void {
    this.form = new FormGroup<CadastroLancamentoForm>({
      categoriaId: new FormControl<number>(0, {
        nonNullable: true,
        validators: Validators.required,
      }),
      descricao: new FormControl<string>('', {
        nonNullable: true,
        validators: Validators.required,
      }),
      dataLancamento: new FormControl<string>('', {
        nonNullable: true,
        validators: Validators.required,
      }),
      valor: new FormControl<string>('0', {
        nonNullable: true,
        validators: [Validators.required, Validators.min(1)],
      }),
      tipoLancamento: new FormControl<TipoLancamento | ''>('', {
        nonNullable: true,
        validators: Validators.required,
      }),
      cartaoId: new FormControl<number>(0, { nonNullable: true }),
    });

    this.inicializarDropdowns();
  }

  inicializarDropdowns(): void {
    forkJoin({
      categorias: this.service.listarCategoriasDisponiveis(),
      cartoes: this.service.listarCartoesDisponiveis(),
    }).subscribe({
      next: (resultado) => {
        this.categoriasAtivas = resultado.categorias;
        this.cartoesAtivos = resultado.cartoes;
      },
      error: () => {
        this.toast.error('Erro ao carregar dropdowns');
      },
    });
  }

  protected idDespesa() {
    return this.form.controls.tipoLancamento.value === 'DESPESA';
  }

  protected handleTipoChange() {
    if (!this.idDespesa()) {
      this.form.controls.cartaoId.setValue(0);
      this.form.controls.cartaoId.setErrors(null);
    }
  }

  protected isFormInvalid(): boolean {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.toast.error('Erro de validação. Verifique os valores informados.');
      return true;
    }
    return false;
  }

  protected handleSubmit() {
    if (this.isFormInvalid()) {
      return;
    }
    let dadosForm: DadosLancamentoForm = this.form.value as DadosLancamentoForm;
    dadosForm.valor = this.parseValor(this.form.controls.valor.value);
    console.log(dadosForm);
    this.service.criar(dadosForm).subscribe({
      next: () => {
        this.toast.success('Lançamento cadastrado com sucesso!');
        this.form.reset();
      },
      error: (error) => {
        this.onApiError(error);
      },
    });
  }

  protected handleCancel() {
    this.form.reset();
  }

  private aplicarErrorValidacao(error: ValidationErrorResponse) {
    error.camposInvalidos.forEach((invalido) => {
      console.log('campo invalido', invalido);
      const control = this.form.get(invalido.campo);
      if (control) {
        control.setErrors({ apiError: invalido.mensagem });
        control.markAsTouched();
      }
    });
  }

  private parseValor(valorMascara: string) :string {
    return valorMascara.replace(/\./g,'').replace(',', '.');
  }

  private onApiError(response: any) {
    if (response.status === 422) {
      this.aplicarErrorValidacao(response.error);
      this.toast.error('Erro de validação verificque');
      return;
    }
  }
}
