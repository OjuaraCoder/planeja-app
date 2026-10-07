import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CartaoService } from '../cartao-service';
import { DadosCartaoForm, DetalhesCartao } from '../dados-cartao';
import { ValidationErrorResponse } from '../../common/validation/validation-error-model';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

interface CadastroCartaoForm{
  nome: FormControl<string>;
  bandeira: FormControl<string>;
}


@Component({
  selector: 'app-cadastro-cartao',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './cadastro-cartao.html',
  styleUrl: './cadastro-cartao.scss',
})
export class CadastroCartao implements OnInit {

  service: CartaoService = inject(CartaoService);
  rotaAtiva: ActivatedRoute = inject(ActivatedRoute);
  toast: ToastrService = inject(ToastrService);
  form!: FormGroup<CadastroCartaoForm>;
  idCartaoEdicao?: string | null = null;


  ngOnInit(): void {
    this.form = new FormGroup<CadastroCartaoForm>({
      nome: new FormControl('', { nonNullable: true, validators: Validators.required }),
      bandeira: new FormControl('', { nonNullable: true, validators: Validators.required }),
    });

    this.carregarDadosEdicao();

  }

  carregarDadosEdicao() {
    this.idCartaoEdicao = this.rotaAtiva.snapshot.queryParamMap.get('id') ?? null;

    if(!this.idCartaoEdicao){
      return;
    }

    this.service.obterPorId(this.idCartaoEdicao)
      .subscribe({
        next:(cartao) => {
          this.form.patchValue({
            nome: cartao.nome,
            bandeira: cartao.bandeira,
          })
        },
        error: () => this.toast.error('Erro ao carregar dados do cartão')
      })
  }

  isFormInvalid(): boolean {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.toast.warning('Erro Lógico');
      return true;
    }
    return false;
  }

  handleSubmit(): void {
    if (this.isFormInvalid()) {
      return;
    }

    const dadosCartao: DadosCartaoForm = this.form.value as DadosCartaoForm;

    const requisicao: Observable<DetalhesCartao | void> = this.idCartaoEdicao
      ? this.service.atualizarCartao(this.idCartaoEdicao, dadosCartao)
      : this.service.criarCartao(dadosCartao);

    requisicao.subscribe({
      next: (response) => {
        this.toast.success('Cartão cadastrado/atualizado com sucesso!');
      },
      error: (error: any) => {
        this.onApiError(error);
      },
    });
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
