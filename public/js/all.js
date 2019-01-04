$(document).ready(function(){
	$("form[name=Filter]").submit(function(e){
		e.preventDefault();
		$.post($(this).attr("data-url"), $(this).serialize(), function(data){
			$("#tableCrud > tbody").html(data);
		}, 'html');
	}).submit();
	

	$("#tableCrud").on("click", ".btn-remove", function(e) {
		if (!confirm("Deseja mesmo excluir?")) {
			e.preventDefault();
		} else {
			$.post($(this).attr("data-url"), $(this).serialize(), function(data){
				$("");
			}, 'html');
		}
	});
	
	$(this).on("click", ".alt-add", function() {
		$(".questao-alternativa").append(
				$("<div>").attr("class", "alt row").append(
						$("<div>").attr("class", "col-md-8").append(
							$("<input>").attr({
								type : "text", "class" : "form-control", name : "alternativa[]", value : "", placeholder : "Informe a alternativa"
						}))).append(
							$("<div>").attr("class", "col-md-1").append(
								$("<input>").attr({
									type : "radio", name : "correta[]"
						}))).append(
							$("<div>").attr("class", "col-md-3").append(
								$("<button>").addClass("btn btn-default glyphicon glyphicon-trash").html("").click(function(){
								$(this).parent().parent().remove();
						}))));
	})
});

function load_modal(url) {
	$.get(url, function(data){
		$("#generic_modal_content").html(data).parent().parent().modal("show");
	}, 'html');
}