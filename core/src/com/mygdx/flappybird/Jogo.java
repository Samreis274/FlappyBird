package com.mygdx.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Jogo extends ApplicationAdapter {
	//cria uma variavel batch
	private SpriteBatch batch;
	//Array de Texture para as posiçoes do passaro
	private Texture[] passaros;
	//criando uma Texture para o fundo
	private Texture fundo;
	//criando uma Texture para o cano de baixo
	private Texture canobaixo;
	//criando uma Texture para o cano do topo
	private Texture canotopo;
	//criando uma texture para o gameover
	private Texture gameOver;

	private int estadoJogo = 0;

	//variaveis float para largura e altura
	private float larguradispositivo;
	private float alturadispositivo;

	//variaveis dos canos
	private float posicaoCanoHorizontal;
	private float posicaoCanoVertical;
	private float espacoEntreCanos;

	//Variveis para movimentação do passaro
	private int pontos = 0;
	private int gravidade = 0;
	float variacao= 0;
	float posicaoInicialVerticalPassaro= 0;

	//bitmap para criar a pontuacao
	BitmapFont textoPontuacao;
	//bitmap para criar o texto de reiniciar
	BitmapFont textoReiniciar;
	//bitmap para criar a melhor pontuacao
	BitmapFont textoMelhorPontuacao;
	//booleana para passaro passar do cano
	private boolean passouCano = false;
	//variavel random
	private Random random;

	//colider dos objetos
	private ShapeRenderer shapeRenderer;
	private Circle circulopassaro;
	private Rectangle retangulocanocima;
	private Rectangle retangulocanobaixo;


	@Override
	public void create () {

		//metodo de declarações das informações do texture
		inicialiazarTexturas();
		//metodo das variaveis com os objetos
		inicializaObjetos();

	}

	@Override
	public void render () {

		//metodo toque na tela
		verificaEstadoJogo();
		//metodo do validar pontos
		validarPontos();
		//metodo de setar imagens na tela e movimentação
		desenharTexturas();
		//metodo de detectar a colisao
		detectarColisao();


	}

	private void inicializaObjetos() {
		//declara um novo random
		random = new Random();
		//declara uma nova batch
		batch = new SpriteBatch();

		//pega a largura do dispositivo
		larguradispositivo = Gdx.graphics.getWidth();
		//pega a altura do dispositivo
		alturadispositivo = Gdx.graphics.getHeight();
		//coloca o valor na posição inial que é a altura do dispositivo divido por 2 que seria o meio da tela
		posicaoInicialVerticalPassaro= alturadispositivo / 2;
		//variavel posicao horizontal recebe a largura do dispositivo
		posicaoCanoHorizontal = larguradispositivo;
		espacoEntreCanos = 350;

		//declara um novo bitmap
		textoPontuacao = new BitmapFont();
		//colocar cor no numero de pontuacao
		textoPontuacao.setColor(Color.WHITE);
		//coloca um tamanho no numero de pontuacao
		textoPontuacao.getData().setScale(10);

		//declara um novo bitmap
		textoReiniciar = new BitmapFont();
		//colocar cor no texto do reniciar
		textoReiniciar.setColor(Color.GREEN);
		//coloca um tamanho no texto do reiniciar
		textoReiniciar.getData().setScale(3);

		//declara um novo bitmap
		textoMelhorPontuacao = new BitmapFont();
		//colocar cor no numero de melhor pontuacao
		textoMelhorPontuacao.setColor(Color.RED);
		//coloca um tamanho no numero de melhor pontuacao
		textoMelhorPontuacao.getData().setScale(3);

		//declara um novo shaperenderer
		shapeRenderer = new ShapeRenderer();
		//declara um novo circle
		circulopassaro = new Circle();
		//declara um novo rectangle para o cano de baixo
		retangulocanobaixo = new Rectangle();
		//declara um novo rectangle para o cano de cima
		retangulocanocima = new Rectangle();


	}



	private void inicialiazarTexturas() {
		//aplicando um tamanho para o Array dos passaros
		passaros = new Texture[3];
		//colando as imagem no array
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		//pega o assets do fundo e coloca na Texture fundo
		fundo = new Texture("fundo.png");
		//pega o assets do cano de baixo e coloca na Texture canobaixo
		canobaixo = new Texture("cano_baixo_maior.png");
		//pega o assets do cano do topo e coloca na Texture canotopo
		canotopo = new Texture("cano_topo_maior.png");
		//pega o assets do gamerover e coloca na Texture gameover
		gameOver = new Texture("game_over.png");
	}



	private void detectarColisao() {
		//configurando o colider do passaro
		circulopassaro.set(50 + passaros[0].getWidth() / 2,
				posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);
		//configura o colider do cano de baixo
		retangulocanobaixo.set(posicaoCanoHorizontal,
				alturadispositivo / 2 - canobaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical,
				canobaixo.getWidth(),canobaixo.getHeight());
		//configura o colider do cano de cima
		retangulocanocima.set(posicaoCanoHorizontal,
				alturadispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical,
				canotopo.getWidth(), canotopo.getHeight());

		//verifica se teve a colisao do passaro com o cano de cima
		boolean bateucanocima = Intersector.overlaps(circulopassaro,retangulocanocima);
		//verifica se teve a colisao do passaro com o cano de baixo
		boolean bateucanobaixo = Intersector.overlaps(circulopassaro,retangulocanobaixo);

		//if para teste de colisao
		if (bateucanobaixo || bateucanocima){
			Gdx.app.log("Log", "bateu");
			//muda o estado do jogo para
			estadoJogo = 2;
		}

	}


	private void validarPontos() {
		//if para validar pontos
		if(posicaoCanoHorizontal<50 - passaros[0].getWidth()) {
			if(!passouCano){
				pontos++;
				//muda a booleana para verdadeira
				passouCano =true;
			}
		}
		//tempo para variação das mudanças da animação do passaro
		variacao += Gdx.graphics.getDeltaTime() * 10;
		//condicao para variavel variacao se ela for maior que 3 ela é zerada
		if(variacao >3)
			//zerando a variavel variacao
			variacao = 0;

	}

	private void verificaEstadoJogo() {

		//cria uma booleana para chamar o metodo de toque na tela
		boolean toqueTela = Gdx.input.justTouched();
		if(estadoJogo == 0){
			//caso tenha toque na tela aplica uma gravidade -15
			if (toqueTela){
				gravidade = -15;
				//muda o estado do jogo pra 1
				estadoJogo = 1;
			}

			//else para condicao caso o estado do jogo esteja no 1
		}else if (estadoJogo == 1 ){

			//caso tenha toque na tela aplica uma gravidade -15
			if (toqueTela){
				gravidade = -15;
				estadoJogo = 1;
			}

			posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;
			//if para variar o posicionamento da abertura do cano
			if(posicaoCanoHorizontal <-canobaixo.getHeight()){
				posicaoCanoHorizontal = larguradispositivo;
				posicaoCanoVertical = random.nextInt(480) - 200;
				//coloca a booleana como falsa
				passouCano = false;
			}
			//if para que sempre q tocar na tela o pesonagem sair e sair da posiçao 0
			if(posicaoInicialVerticalPassaro > 0 || toqueTela);
			// faz a posiçao vertical menos a gravidade para fazer o passaro cair
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

			//contador de gravidade
			gravidade ++;
			//verifica se o estado do jogo é o 2
		}else if (estadoJogo == 2){

		}

	}

	private void desenharTexturas() {
		//inicia a renderização
		batch.begin();

		//desenha a imagem puxada do create
		batch.draw(fundo, 0, 0,larguradispositivo,alturadispositivo);
		//desenha o personagem na tela e faz a movimentação trocando as assets
		batch.draw(passaros[(int) variacao], 50, posicaoInicialVerticalPassaro);
		//desenha o cano de baixo na tela
		batch.draw(canobaixo,posicaoCanoHorizontal, alturadispositivo/2 - canobaixo.getHeight() -
				espacoEntreCanos/2 + posicaoCanoVertical);
		//desenha o cano de cima na tela
		batch.draw(canotopo, posicaoCanoHorizontal, alturadispositivo/2  + espacoEntreCanos/2 +posicaoCanoVertical);
		//desenha a pontuacao na tela
		textoPontuacao.draw(batch, String.valueOf(pontos), larguradispositivo/2, alturadispositivo - 100);

		if(estadoJogo == 2) {
			//desenha a mensagem de gameOver na tela
			batch.draw(gameOver, larguradispositivo / 2 - gameOver.getWidth()/2, alturadispositivo / 2);
			//desenha o texto reiniciar no meio da tela
			textoReiniciar.draw(batch, "TOQUE NA TELA PARA REINICIAR",
					larguradispositivo / 2 -350, alturadispositivo / 2 - gameOver.getHeight()/2);
			//desenha o numero da melhor pontuacao no meio da tela
			textoReiniciar.draw(batch, String.valueOf(pontos),
					larguradispositivo / 2 , alturadispositivo / 2 - gameOver.getHeight()*2);
		}
		//termina a seguencia a aplicação
		batch.end();

	}



	@Override
	public void dispose () {

	}
}
