import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CartaoService } from '../cartao-service';
import { DadosCartaoForm, DetalhesCartao } from '../dados-cartao';
import { ValidationErrorResponse } from '../../common/validation/validation-error-model';
import { CommonModule } from '@angular/common';

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
  form!: FormGroup<CadastroCartaoForm>;
  service = inject(CartaoService);

  ngOnInit(): void {
    this.form = new FormGroup<CadastroCartaoForm>({
      nome: new FormControl('', { nonNullable: true, validators: Validators.required }),
      bandeira: new FormControl('', { nonNullable: true, validators: Validators.required }),
    });
  }

  isFormInvalid(): boolean {
    if(this.form.invalid) {
      this.form.markAllAsTouched();
      return true;
    }
    return false;
  }

  handleSubmit(): void {
    if (this.isFormInvalid()) {
      return;
    }

    const dadosCartao: DadosCartaoForm = this.form.value as DadosCartaoForm;
    this.service.criarCartao(dadosCartao).subscribe({
      next: (response:DetalhesCartao) => {
        console.log('resposta do servidor: ', response);
      },
      error: (error:any) => this.onApiError(error)
    });

  }

  private aplicarErrorValidacao(error: ValidationErrorResponse){
    error.camposInvalidos.forEach(invalido => {
        console.log('campo invalido', invalido);
        const control = this.form.get(invalido.campo);
      console.log('control', control);
        if(control){
          control.setErrors({ apiError: invalido.mensagem })
          control.markAsTouched();
        }
    });
  }

  private onApiError(response: any){
    if (response.status === 422) {
      this.aplicarErrorValidacao(response.error);
      return;
    }

  }
}
