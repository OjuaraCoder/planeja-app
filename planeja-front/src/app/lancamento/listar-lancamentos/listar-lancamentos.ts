import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { DetalhesLancamento, TipoLancamento } from '../lancamento-dados';
import { RouterLink } from '@angular/router';
import { Header } from '../../common/components/header/header';
import { CommonModule } from '@angular/common';
import { LancamentoService } from '../lancamento-service';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { PageResult } from '../../common/pagination/page-result';
import { DetalhesCategoria } from '../../categoria/categoria-dados';


interface FiltrosForm{
  mes: FormControl<string>;
  tipoLancamento: FormControl<TipoLancamento | ''>;
  categoriaId: FormControl<number>;
}

@Component({
  selector: 'app-listar-lancamentos',
  imports: [CommonModule, RouterLink, ReactiveFormsModule, Header],
  templateUrl: './listar-lancamentos.html',
  styleUrl: './listar-lancamentos.scss',
})
export class ListarLancamentos implements OnInit {
  service = inject(LancamentoService);
  toast = inject(ToastrService);

  listagem$!: Observable<PageResult<DetalhesLancamento>>;
  form!: FormGroup;
  categorias: DetalhesCategoria[] = [];
  paginaAtual = 0;
  tamanhoPagina = 10;


  ngOnInit(): void {
    this.form = new FormGroup<FiltrosForm>({
      mes: new FormControl<string>('', { nonNullable: true }),
      tipoLancamento: new FormControl<TipoLancamento | ''>('', { nonNullable: true }),
      categoriaId: new FormControl<number>(0, { nonNullable: true }),
    });

    this.listarLancamentos();

  }

  listarLancamentos(){
    const tipoLancamento = this.form.get("tipoLancamento")?.value;
    const categoriaId = this.form.get("categoriaId")?.value;
    const mes = this.form.get("mes")?.value;

    this.listagem$ = this.service.listarLancamentos({
      page: this.paginaAtual,
      size: this.tamanhoPagina,
      mes: mes || undefined,
      tipoLancamento: tipoLancamento || undefined,
      categoriaId: categoriaId || undefined
    });


  }

  aplicarFiltros(){
    this.paginaAtual = 0;
    this.listarLancamentos();
  }

}
